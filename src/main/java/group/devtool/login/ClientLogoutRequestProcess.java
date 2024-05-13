package group.devtool.login;

/**
 * 客户端登出处理器，拦截客户端登出请求并转发至登录服务完成登出。
 */
public class ClientLogoutRequestProcess implements RequestInterceptProcess {

  private RequestResponseProcess requestResponseProcess;
  
  private LoginClientProperties properties;

  private LoginTokenManager manager;

  private LoginRedirection redirection;

  private LoginProtocolFactory protocol;

  /**
   * 客户端登录退出处理器
   * 
   * @param requestResponseProcess 请求及响应处理器
   */
  public ClientLogoutRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginClientConfiguration.properties();
    this.manager = LoginClientConfiguration.manager();
    this.redirection = LoginClientConfiguration.redirection();
    this.protocol = LoginClientConfiguration.protocol();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.clientLogoutUrl());
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    LoginToken token = manager.resolve((name) -> {
      return requestResponseProcess.cookie(request, name);
    });
    if (null != token) {
      manager.clean(token.id());
    }
    String logoutRedirectUrl = redirection.logoutRedirectUrl(request);
    LoginProtocolResponseFactory redirectFactory = protocol.response(RedirectServerLogoutResponse.class);
    RedirectServerLogoutResponse redirectResponse = redirectFactory.create(properties, logoutRedirectUrl);
    requestResponseProcess.redirect(response, redirectResponse);
  }

}
