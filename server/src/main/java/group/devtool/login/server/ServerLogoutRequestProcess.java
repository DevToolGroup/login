package group.devtool.login.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import group.devtool.login.client.LoginApplication;
import group.devtool.login.client.LoginUtils;
import group.devtool.login.client.RequestInterceptProcess;
import group.devtool.login.client.RequestResponseProcess;

/**
 * 统一登出及过期处理，拦截客户端的登出请求，清理登录状态，并跳转至来源应用地址
 */
public class ServerLogoutRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private LoginTicket loginTicket;

  private TicketRepository repository;

  private LoginServerProperties properties;

  private LogoutService logoutService;

  private LoginAuthenticate authenticate;

  private ApplicationRepository application;

  public ServerLogoutRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.repository = LoginServerConfiguration.repository();
    this.properties = LoginServerConfiguration.properties();
    this.logoutService = LoginServerConfiguration.logoutService();
    this.authenticate = LoginServerConfiguration.authenticate();
    this.application = LoginServerConfiguration.applications();
  }

  /**
   * 如果是登出请求，或者凭证过期则执行退出逻辑
   * 
   * @param request 请求
   */
  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.logoutUrl());
  }

  /**
   * 通知已登录应用登出，当前请求跳转至登录页面
   */
  @Override
  public <Q, R> void process(Q request, R response) {
    loginTicket = (LoginTicket) repository.resolve((name) -> {
      return requestResponseProcess.cookie(request, name);
    });
    repository.remove(loginTicket.id());
    Map<String, RegisterApplication> loggedApplications = getLoggedApplication();
    // 通知已登录应用退出
    for (Map.Entry<String, RegisterApplication> entry : loggedApplications.entrySet()) {
      String logoutUrl = entry.getValue().logoutUrl();
      if (null == logoutUrl) {
        continue;
      }
      String clientLogoutUrl = LoginUtils.constructClientLogoutUrl(logoutUrl, entry.getKey());
      logoutService.logout(clientLogoutUrl);
    }
    // 构造跳转请求
    String url = requestResponseProcess.logoutRedirectUrl(request);
    String requestUrl = LoginUtils.redirectAuthorizeUrl(properties.authenticateUrl(), url);
    requestResponseProcess.redirect(response, authenticate.loginUrl(requestUrl));
  }

  private Map<String, RegisterApplication> getLoggedApplication() {
    Collection<String> appIds = getApplicationId();
    Map<String, RegisterApplication> apps = application.getApplication(appIds).stream()
        .collect(Collectors.toMap(i -> i.id(), i -> i));

    Map<String, RegisterApplication> result = new HashMap<>();
    for (Map.Entry<String, LoginApplication> entry : loginTicket.loggedApplication().entrySet()) {
      result.put(entry.getKey(), apps.get(entry.getValue().appId()));
    }
    return result;
  }

  private List<String> getApplicationId() {
    Map<String, LoginApplication> loggedApplications = loginTicket.loggedApplication();
    return loggedApplications.values()
        .stream()
        .map(i -> i.appId())
        .collect(Collectors.toList());
  }

}
