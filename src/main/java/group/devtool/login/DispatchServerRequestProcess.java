package group.devtool.login;


/**
 * 登录服务相关请求处理器
 */
public class DispatchServerRequestProcess extends DispatchRequestProcess {

  private LoginServerProperties properties;

  /**
   * 初始化登录服务请求处理器
   * 
   * @param requestResponseProcess 请求及响应参数处理器
   * @param processes              登录服务相关请求处理器
   */
  public DispatchServerRequestProcess(RequestResponseProcess requestResponseProcess,
      RequestInterceptProcess... processes) {
    super(requestResponseProcess, processes);
    properties = LoginServerConfiguration.properties();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = getRequestResponseProcess().url(request);
    return url.startsWith(properties.loginContext());
  }
}
