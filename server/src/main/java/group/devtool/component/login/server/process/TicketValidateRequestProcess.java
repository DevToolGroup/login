package group.devtool.component.login.server.process;

import group.devtool.component.login.core.AntPathMatch;
import group.devtool.component.login.core.entity.LoginToken;
import group.devtool.component.login.core.entity.ServiceTicket;
import group.devtool.component.login.core.repository.LoginTicketRepository;
import group.devtool.component.login.core.RsaUtils;
import group.devtool.component.login.core.repository.ServiceRepository;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.protocol.LoginProtocolRequestFactory;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.core.service.RegisterService;
import group.devtool.component.login.server.LoginServerConfiguration;
import group.devtool.component.login.server.LoginServerProperties;
import group.devtool.component.login.core.exception.LoginException;
import group.devtool.component.login.core.exception.LoginValidateException;
import group.devtool.component.login.core.exception.TicketExpiredException;
import group.devtool.component.login.core.exception.TicketNotFoundException;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.server.protocol.request.ClientTicketValidateRequest;
import group.devtool.component.login.core.protocol.TicketValidateResponse;

/**
 * 应用授权凭证验证处理器
 */
public class TicketValidateRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private final LoginServerProperties properties;

  private final LoginTicketRepository manager;

  private final ServiceRepository applications;

  private final LoginProtocolFactory protocol;

  /**
   * 初始化凭证校验处理器
   * 
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public TicketValidateRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginServerConfiguration.properties();
    this.manager = LoginServerConfiguration.manager();
    this.applications = LoginServerConfiguration.applications();
    this.protocol = LoginServerConfiguration.protocol();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return AntPathMatch.INS.match(properties.validateUrl(), url);

  }

  @Override
  public <Q, R> void process(Q request, R response) {
    LoginProtocolRequestFactory factory = protocol.request(ClientTicketValidateRequest.class);
    ClientTicketValidateRequest validateRequest = factory.create(request, requestResponseProcess);
    try {
      // 登录凭证更新
      LoginToken token = manager.synchronize(() -> {
        ServiceTicket appTicket = manager.getAppTicket(validateRequest.appTicketId());
        if (null == appTicket) {
          throw new TicketNotFoundException(validateRequest.appTicketId());
        }
        if (appTicket.isExpired()) {
          throw new TicketExpiredException(appTicket.id());
        }
        return appTicket.id();
      }, () -> {
        ServiceTicket appTicket = manager.getAppTicket(validateRequest.appTicketId());
        if (appTicket.isExpired()) {
          throw new TicketExpiredException(appTicket.id());
        }
        RegisterService application = applications.getApplication(appTicket.service().appId());
        // 校验签名是否合法
        boolean result = RsaUtils.verify(application.publicKey(), appTicket.id(), validateRequest.signAppTicketId());
        if (!result) {
          throw new LoginValidateException(appTicket.id());
        }
        LoginToken loginToken = appTicket.confirm();
        manager.saveOrUpdate(appTicket.loginTicket());
        return loginToken;
      });

      LoginProtocolResponseFactory responseFactory = protocol.response(TicketValidateResponse.class);
      TicketValidateResponse validateResponse = responseFactory.create(token);
      requestResponseProcess.success(response, validateResponse);

    } catch (LoginException e) {
      requestResponseProcess.error(response, e);
    }

  }
}
