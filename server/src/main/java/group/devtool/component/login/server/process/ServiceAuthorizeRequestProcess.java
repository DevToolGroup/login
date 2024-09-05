package group.devtool.component.login.server.process;

import group.devtool.component.login.core.AntPathMatch;
import group.devtool.component.login.core.entity.LoginTicket;
import group.devtool.component.login.core.entity.ServiceTicket;
import group.devtool.component.login.core.exception.LoginException;
import group.devtool.component.login.core.exception.ServiceNotFoundException;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.repository.LoginTicketRepository;
import group.devtool.component.login.core.repository.ServiceRepository;
import group.devtool.component.login.core.protocol.LoginProtocolRequestFactory;
import group.devtool.component.login.server.protocol.request.RedirectServiceAuthorizeRequest;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.server.protocol.response.RedirectAuthenticateResponse;
import group.devtool.component.login.server.protocol.response.RedirectServiceTicketResponse;
import group.devtool.component.login.core.service.LoginAuthenticate;
import group.devtool.component.login.core.service.RegisterService;
import group.devtool.component.login.core.service.TicketIdGenerator;
import group.devtool.component.login.server.LoginServerConfiguration;
import group.devtool.component.login.server.LoginServerProperties;

/**
 * 应用登录授权请求处理器
 */
public class ServiceAuthorizeRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private final TicketIdGenerator idGenerator;

  private final LoginTicketRepository manager;

  private final LoginAuthenticate authenticate;

  private final LoginServerProperties properties;

  private final ServiceRepository applications;

  private final LoginProtocolFactory protocol;

  /**
   * 初始化应用授权请求处理器
   * 
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public ServiceAuthorizeRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginServerConfiguration.properties();
    this.applications = LoginServerConfiguration.applications();
    this.manager = LoginServerConfiguration.manager();
    this.idGenerator = LoginServerConfiguration.idGenerator();
    this.authenticate = LoginServerConfiguration.authenticate();
    this.protocol = LoginServerConfiguration.protocol();
  }

  @Override
  public <Q> boolean match(Q request) {
    String requestUrl = requestResponseProcess.url(request);
    return AntPathMatch.INS.match(properties.authorizeUrl(), requestUrl);
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    // 如果已登录，则授权，相反跳转至登录
    if (logged(request)) {
      authorize(request, response);
    } else {
      login(request, response);
    }
  }

  private <Q> boolean logged(Q request) {
    // 解析登录凭证
    LoginTicket loginTicket = (LoginTicket) manager.resolve(name -> requestResponseProcess.cookie(request, name));
    return null != loginTicket && !loginTicket.isExpired();
  }

  private <Q, R> void authorize(Q request, R response) {
    try {
      RedirectServiceAuthorizeRequest authorizeRequest = parseAuthorizeRequest(request);
      RegisterService registerApplication = findRegisterApplication(authorizeRequest.service().appId());

      // 生成应用授权登录凭证
      ServiceTicket appTicket = manager.synchronize(() -> {
        LoginTicket loginTicket = (LoginTicket) manager.resolve(name -> requestResponseProcess.cookie(request, name));
        return loginTicket.id();
      }, () -> {
        LoginTicket loginTicket = (LoginTicket) manager.resolve(name -> requestResponseProcess.cookie(request, name));
        return loginTicket.createTicket(idGenerator.nextId(),
            authorizeRequest.service(),
            registerApplication.expirePolicy());
      });

      // 携带应用授权凭证跳转应用ticket校验地址
      LoginProtocolResponseFactory redirectFactory = protocol.response(RedirectServiceTicketResponse.class);
      RedirectServiceTicketResponse redirectResponse = redirectFactory.create(registerApplication, appTicket);
      manager.saveOrUpdate(appTicket);
      requestResponseProcess.redirect(response, redirectResponse);
    } catch (LoginException e) {
      e.printStackTrace();
      requestResponseProcess.error(response, e);
    }
  }

  private RegisterService findRegisterApplication(String appId) throws LoginException {
    RegisterService registerApplication = applications.getApplication(appId);
    if (null == registerApplication) {
      throw new ServiceNotFoundException(appId);
    }
    return registerApplication;
  }

  private <Q, R> void login(Q request, R response) {
    RedirectServiceAuthorizeRequest authorizeRequest = parseAuthorizeRequest(request);
    LoginProtocolResponseFactory redirectFactory = protocol.response(RedirectAuthenticateResponse.class);
    RedirectAuthenticateResponse redirectResponse = redirectFactory.create(properties, authorizeRequest.service());
    requestResponseProcess.redirect(response, authenticate.loginUrl(redirectResponse));
  }

  private <Q> RedirectServiceAuthorizeRequest parseAuthorizeRequest(Q request) {
    LoginProtocolRequestFactory requestFactory = protocol.request(RedirectServiceAuthorizeRequest.class);
    return requestFactory.create(request, requestResponseProcess);
  }
}
