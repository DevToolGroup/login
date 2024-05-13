package group.devtool.login;

/**
 * 业务相关请求处理器
 */
public class DispatchClientRequestProcess extends DispatchRequestProcess {

  private LoginServerProperties properties;

  /**
   * 初始化业务相关请求处理器
   * 
   * @param requestResponseProcess 请求响应处理器
   * @param processes              请求处理器
   */
  public DispatchClientRequestProcess(RequestResponseProcess requestResponseProcess,
      RequestInterceptProcess... processes) {
    super(requestResponseProcess, processes);
    properties = LoginServerConfiguration.properties();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = getRequestResponseProcess().url(request);
    return !url.startsWith(properties.loginContext());
  }

}
