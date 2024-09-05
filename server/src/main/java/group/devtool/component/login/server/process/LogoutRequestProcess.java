package group.devtool.component.login.server.process;

import group.devtool.component.login.core.AntPathMatch;
import group.devtool.component.login.core.entity.LoginTicket;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.protocol.LoginProtocolRequestFactory;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.core.service.LoginAuthenticate;
import group.devtool.component.login.core.service.LoginService;
import group.devtool.component.login.core.service.LogoutService;
import group.devtool.component.login.core.service.RegisterService;
import group.devtool.component.login.server.LoginServerConfiguration;
import group.devtool.component.login.server.LoginServerProperties;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.core.repository.LoginTicketRepository;
import group.devtool.component.login.core.repository.ServiceRepository;
import group.devtool.component.login.server.protocol.request.ClientLogoutRequest;
import group.devtool.component.login.server.protocol.request.RedirectServerLogoutRequest;
import group.devtool.component.login.server.protocol.response.RedirectAuthenticateResponse;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统一登出及过期处理，拦截客户端的登出请求，清理登录状态，并跳转至来源应用地址
 */
public class LogoutRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private LoginTicket loginTicket;

  private final LoginTicketRepository manager;

  private final LoginServerProperties properties;

  private final LogoutService logoutService;

  private final LoginAuthenticate authenticate;

  private final ServiceRepository application;

  private final LoginProtocolFactory protocol;

  /**
   * 初始化登出请求处理器
   * 
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public LogoutRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.manager = LoginServerConfiguration.manager();
    this.properties = LoginServerConfiguration.properties();
    this.logoutService = LoginServerConfiguration.logoutService();
    this.authenticate = LoginServerConfiguration.authenticate();
    this.application = LoginServerConfiguration.applications();
    this.protocol = LoginServerConfiguration.protocol();
  }

  /**
   * 如果是登出请求，或者凭证过期则执行退出逻辑
   * 
   * @param request 请求
   */
  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return AntPathMatch.INS.match(properties.logoutUrl(), url);

  }

  /**
   * 通知已登录应用登出，当前请求跳转至登录页面
   */
  @Override
  public <Q, R> void process(Q request, R response) {
    LoginProtocolRequestFactory requestFactory = protocol.request(RedirectServerLogoutRequest.class);
    RedirectServerLogoutRequest logoutRequest = requestFactory.create(request, requestResponseProcess);
    // 删除登录凭证
    loginTicket = (LoginTicket) manager.resolve(name -> requestResponseProcess.cookie(request, name));
    manager.clean(loginTicket.id());
    // 通知已登录应用退出
    Map<String, RegisterService> loggedApplications = getLoggedApplication();
    for (Map.Entry<String, RegisterService> entry : loggedApplications.entrySet()) {
      RegisterService registerApplication = entry.getValue();
      String clientLogoutUrl = registerApplication.logoutUrl();
      if (null == clientLogoutUrl || registerApplication.id().equals(logoutRequest.application().appId())) {
        continue;
      }
      LoginProtocolRequestFactory logoutRequestFactory = protocol.request(ClientLogoutRequest.class);
      ClientLogoutRequest clientLogoutRequest = logoutRequestFactory.create(registerApplication.logoutUrl(),
          entry.getKey());
      logoutService.logout(clientLogoutRequest);
    }
    // 构造跳转请求
    LoginProtocolResponseFactory redirectFactory = protocol.response(RedirectAuthenticateResponse.class);
    RedirectAuthenticateResponse authenticateResponse = redirectFactory.create(properties, logoutRequest.application());
    requestResponseProcess.redirect(response, authenticate.loginUrl(authenticateResponse));
  }

  private Map<String, RegisterService> getLoggedApplication() {
    Collection<String> appIds = getApplicationId();
    Map<String, RegisterService> apps = application.getApplication(appIds).stream()
        .collect(Collectors.toMap(RegisterService::id, i -> i));

    Map<String, RegisterService> result = new HashMap<>();
    for (Map.Entry<String, LoginService> entry : loginTicket.loggedApplication().entrySet()) {
      result.put(entry.getKey(), apps.get(entry.getValue().appId()));
    }
    return result;
  }

  private List<String> getApplicationId() {
    Map<String, LoginService> loggedApplications = loginTicket.loggedApplication();
    return loggedApplications.values()
        .stream()
        .map(LoginService::appId)
        .collect(Collectors.toList());
  }

}
