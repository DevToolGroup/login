package group.devtool.login.client;

/**
 * 客户端登出处理器，拦截客户端登出请求并转发至登录服务完成登出。
 */
public class ClientLogoutRequestProcess implements RequestInterceptProcess {

  private RequestResponseProcess requestResponseProcess;
  
  private LoginClientProperties properties;

  private TokenRepository repository;

  public ClientLogoutRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginClientConfiguration.properties();
    this.repository = LoginClientConfiguration.repository();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.clientLogoutUrl());
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    LoginToken token = repository.resolve((name) -> {
      return requestResponseProcess.cookie(request, name);
    });
    if (null != token) {
      repository.clean(token.id());
    }
    String redirectUrl = requestResponseProcess.logoutRedirectUrl(request);
    String serverLogoutUrl = LoginUtils.constructServerLogoutUrl(properties.serverLogoutUrl(), redirectUrl);
    requestResponseProcess.redirect(response, serverLogoutUrl);
  }

}
