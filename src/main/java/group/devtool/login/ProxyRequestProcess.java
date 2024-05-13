package group.devtool.login;

/**
 * 代理请求处理器，设置登录状态
 */
public class ProxyRequestProcess implements RequestInterceptProcess {

  private LoginTokenManager manager;

  private RequestInterceptProcess wrappedRequestProcess;

  private RequestResponseProcess requestResponseProcess;

  /**
   * 代理请求处理器，主要用于代理业务处理，设置登录状态
   * 
   * @param manager                登录状态处理器
   * @param requestProcess         业务请求处理器
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public ProxyRequestProcess(LoginTokenManager manager,
      RequestInterceptProcess requestProcess,
      RequestResponseProcess requestResponseProcess) {
    this.manager = manager;
    this.wrappedRequestProcess = requestProcess;
    this.requestResponseProcess = requestResponseProcess;
  }

  @Override
  public <Q, R> void process(Q request, R response) throws Exception {
    LoginToken token = manager.resolve((name) -> {
      return requestResponseProcess.cookie(request, name);
    });
    requestResponseProcess.saveLogin(token.authorization());
    try {
      wrappedRequestProcess.process(request, response);
    } finally {
      requestResponseProcess.removeLogin(token.authorization());
    }
  }
}
