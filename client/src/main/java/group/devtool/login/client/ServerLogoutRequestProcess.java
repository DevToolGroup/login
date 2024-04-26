package group.devtool.login.client;

/**
 * 服务端登出请求处理器
 */
public class ServerLogoutRequestProcess implements RequestInterceptProcess {

  private RequestResponseProcess requestResponseProcess;

  private TokenRepository repository;

  private LoginClientProperties properties;

  public ServerLogoutRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.repository = LoginClientConfiguration.repository();
    this.properties = LoginClientConfiguration.properties();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.serverLogoutUrl());
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    String url = requestResponseProcess.url(request);
    String sessionId = LoginUtils.resolveClientLogoutSessionId(url);
    if (null == sessionId) {
      requestResponseProcess.error(response, new InvalidServerLogoutParameterException(url));
      return;
    }
    repository.clean(sessionId);
    requestResponseProcess.success(response, true);
  }

}
