package group.devtool.login.server;

import group.devtool.login.client.LoginException;
import group.devtool.login.client.LoginToken;
import group.devtool.login.client.LoginTokenSerializeException;
import group.devtool.login.client.LoginUtils;
import group.devtool.login.client.RequestInterceptProcess;
import group.devtool.login.client.RequestResponseProcess;
import group.devtool.login.client.TokenSerializer;

/**
 * 应用授权凭证验证处理器
 */
public class TicketValidateRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private LoginServerProperties properties;

  private TicketRepository repository;

  private TokenSerializer serializer;

  private ApplicationRepository applications;

  private ApplicationTicket appTicket;

  public TicketValidateRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    properties = LoginServerConfiguration.properties();
    repository = LoginServerConfiguration.repository();
    serializer = LoginServerConfiguration.serializer();
    applications = LoginServerConfiguration.applications();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.validateUrl());
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    String url = requestResponseProcess.url(request);
    String applicationTicketId = LoginUtils.resolveValidateTicket(url);
    String applicationTicketSign = LoginUtils.resolveValidateSign(url);
    if (null == applicationTicketId || null == applicationTicketSign) {
      requestResponseProcess.error(response, new InvalidTicketValidateParameterException(url));
      return;
    }
    appTicket = repository.getTicket(applicationTicketId, ApplicationTicket.class);
    if (null == appTicket) {
      requestResponseProcess.error(response, new TicketNotFoundException(applicationTicketId));
      return;
    }
    // 校验应用授权凭证
    try {
      valid(applicationTicketSign);
    } catch (LoginException e) {
      requestResponseProcess.error(response, e);
      return;
    }
    // 登录凭证更新
    LoginToken token = repository.lock(appTicket.loginTicket().id(), () -> {
      ApplicationTicket ticket = repository.getTicket(applicationTicketId, ApplicationTicket.class);
      LoginToken loginToken = ticket.confirm();
      repository.updateTicket(ticket);
      return loginToken;
    });
    try {
      requestResponseProcess.success(response, serializer.serialize(token));
    } catch (LoginTokenSerializeException e) {
      requestResponseProcess.error(response, e);
    }
  }

  private void valid(String signTicketId) throws LoginException {
    String ticketId = appTicket.id();

    RegisterApplication application = applications.getApplication(appTicket.application().appId());
    // 校验签名是否合法
    boolean result = LoginUtils.verify(application.publicKey(), ticketId, signTicketId);
    if (!result) {
      throw new LoginValidateException(ticketId);
    }
    // 校验应用登录授权码是否有效
    if (appTicket.isExpired()) {
      throw new TicketExpiredException(ticketId);
    }
    // 校验登录凭证是否过期
    if (appTicket.loginTicket().isExpired()) {
      throw new TicketExpiredException(ticketId);
    }
  }
}
