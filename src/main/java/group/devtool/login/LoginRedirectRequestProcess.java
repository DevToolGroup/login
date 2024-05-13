package group.devtool.login;

/**
 * 请求未登录时，构造应用授权链接，并重定向至登录服务
 */
public class LoginRedirectRequestProcess implements RequestInterceptProcess {
  
  private RequestResponseProcess requestResponseProcess;

  private LoginRedirection redirection;

  private LoginClientProperties properties;

  private LoginProtocolFactory protocol;

  private LoginTokenManager manager;

  /**
   * 构造登录重定向处理器
   * 
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public LoginRedirectRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.redirection = LoginClientConfiguration.redirection();
    this.properties = LoginClientConfiguration.properties();
    this.protocol = LoginClientConfiguration.protocol();
    this.manager = LoginClientConfiguration.manager();
  }

  @Override
  public <Q> boolean match(Q request) {
    LoginToken token = manager.resolve((name) -> {
      return requestResponseProcess.cookie(request, name);
    });
    return null == token || token.isExpired();
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    String loggedRedirectUrl = redirection.loggedRedirectUrl(request);
    if (null == loggedRedirectUrl || loggedRedirectUrl.length() == 0) {
      requestResponseProcess.error(response, new InvalidLoginRedirectUrlException((loggedRedirectUrl)));
      return;
    }
    LoginProtocolResponseFactory redirectFactory = protocol.response(RedirectServiceAuthorizeResponse.class);
    RedirectServiceAuthorizeResponse redirectResponse = redirectFactory.create(properties, loggedRedirectUrl);
    requestResponseProcess.redirect(response, redirectResponse);
  }

}
