package group.devtool.login.server;

import group.devtool.login.client.ApplicationAuthorizeArgument;
import group.devtool.login.client.InvalidLoginAuthorizeParameterException;
import group.devtool.login.client.LoginApplication;
import group.devtool.login.client.LoginException;
import group.devtool.login.client.LoginUtils;
import group.devtool.login.client.RequestInterceptProcess;
import group.devtool.login.client.RequestResponseProcess;

/**
 * 应用登录授权处理器
 */
public class ApplicationAuthorizeRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private IdGenerator idGenerator;

  private TicketRepository repository;

  private LoginAuthenticate authenticate;

  private LoginServerProperties properties;

  private ApplicationRepository applications;

  private ApplicationAuthorizeArgument argument;

  public ApplicationAuthorizeRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginServerConfiguration.properties();
    this.applications = LoginServerConfiguration.applications();
    this.repository = LoginServerConfiguration.repository();
    this.idGenerator = LoginServerConfiguration.idGenerator();
    this.argument = LoginServerConfiguration.argument();
    this.authenticate = LoginServerConfiguration.authenticate();
  }

  @Override
  public <Q> boolean match(Q request) {
    String requestUrl = requestResponseProcess.url(request);
    String authorizeUrl = properties.authorizeUrl();
    return requestUrl.startsWith(authorizeUrl);
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    // 解析登录凭证
    LoginTicket loginTicket = (LoginTicket) repository.resolve((name) -> {
      return requestResponseProcess.cookie(request, name);
    });
    // 如果已登录，则授权，相反跳转至登录
    if (logged(loginTicket)) {
      authorize(loginTicket, request, response);
    } else {
      login(request, response);
    }
  }

  private <Q> boolean logged(LoginTicket loginTicket) {
    return null != loginTicket && !loginTicket.isExpired();
  }

  private <Q, R> void authorize(LoginTicket loginTicket, Q request, R response) {
    String url = requestResponseProcess.url(request);
    String requestUrl = LoginUtils.resolveAuthenticateRequestUrl(url);
    // 解析登录应用
    LoginApplication loginApplication;
    RegisterApplication registerApplication;
    try {
      loginApplication = argument.resolve(requestUrl);
      if (null == loginApplication || null == loginApplication.appId()) {
        throw new InvalidLoginAuthorizeParameterException(requestUrl);
      }
      registerApplication = findRegisterApplication(loginApplication);
    } catch (LoginException e) {
      requestResponseProcess.error(response, e);
      return;
    }

    // 生成应用授权登录凭证
    ApplicationTicket appTicket = loginTicket.createTicket(idGenerator.nextId(),
        loginApplication,
        registerApplication.expirePolicy());
    repository.saveTicket(appTicket);
    // 携带应用授权凭证跳转应用ticket校验地址
    String redirectUrl = LoginUtils.redirectApplicationTicketUrl(registerApplication.validateTicketUrl(),
        appTicket.id());
    requestResponseProcess.redirect(response, redirectUrl);
  }

  private RegisterApplication findRegisterApplication(LoginApplication loginApplication) throws LoginException {
    String appId = loginApplication.appId();
    RegisterApplication registerApplication = applications.getApplication(appId);
    if (null == registerApplication) {
      throw new ApplicationNotFoundException(appId);
    }
    return registerApplication;
  }

  private <Q, R> void login(Q request, R response) {
    String originUrl = requestResponseProcess.url(request);
    String authenticateUrl = LoginUtils.redirectAuthorizeUrl(properties.authenticateUrl(), originUrl);
    requestResponseProcess.redirect(response, authenticate.loginUrl(authenticateUrl));
  }
}
