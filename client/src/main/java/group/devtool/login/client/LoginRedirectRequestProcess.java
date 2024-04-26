package group.devtool.login.client;

/**
 * 登录跳转处理
 */
public class LoginRedirectRequestProcess implements RequestInterceptProcess {
  
  private RequestResponseProcess requestResponseProcess;

  private ApplicationAuthorizeArgument argument;

  private TokenRepository repository;

  private LoginClientProperties properties;

  public LoginRedirectRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.argument = LoginClientConfiguration.argument();
    this.repository = LoginClientConfiguration.repository();
    this.properties = LoginClientConfiguration.properties();
  }

  @Override
  public <Q> boolean match(Q request) {
    LoginToken token = repository.resolve((name) -> {
      return requestResponseProcess.cookie(request, name);
    });
    return null == token || token.isExpired();
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    String originUrl = requestResponseProcess.url(request);
    String requestUrl = argument.construct(originUrl, properties.appId());
    if (null == requestUrl) {
      requestResponseProcess.error(response, new InvalidLoginAuthorizeParameterException(requestUrl));
      return;
    }
    String redirectUrl = LoginUtils.redirectAuthorizeUrl(properties.authorizeUrl(), requestUrl);
    requestResponseProcess.redirect(response, redirectUrl);
  }

}
