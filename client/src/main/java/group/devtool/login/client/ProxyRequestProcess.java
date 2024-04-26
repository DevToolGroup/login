package group.devtool.login.client;

/**
 * 代理请求处理器，设置登录状态
 */
public class ProxyRequestProcess implements RequestInterceptProcess {

  private TokenRepository repository;

  private RequestInterceptProcess wrappedRequestProcess;

  private RequestResponseProcess requestResponseProcess;

  public ProxyRequestProcess(TokenRepository repository,
      RequestInterceptProcess requestProcess,
      RequestResponseProcess requestResponseProcess) {
    this.repository = repository;
    this.wrappedRequestProcess = requestProcess;
    this.requestResponseProcess = requestResponseProcess;
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    LoginToken token = repository.resolve((name) -> {
      return requestResponseProcess.cookie(request, name);
    });
    requestResponseProcess.saveLogin(token.authorization());
    wrappedRequestProcess.process(request, response);
    requestResponseProcess.removeLogin(token.authorization());
  }
}
