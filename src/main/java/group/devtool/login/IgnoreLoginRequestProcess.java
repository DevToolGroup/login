package group.devtool.login;

import java.util.List;

/**
 * 忽略登录状态的业务请求处理器
 */
public class IgnoreLoginRequestProcess implements RequestInterceptProcess {

  private List<String> urls;

  private RequestInterceptProcess wrappedRequestProcess;

  private RequestResponseProcess requestResponseProcess;

  /**
   * 根据参数构造忽略登录状态的业务请求处理器
   * 
   * @param urls 忽律登录状态的请求地址集合
   * @param requestResponseProcess 请求与响应处理器
   * @param requestProcess 业务处理器
   */
  public IgnoreLoginRequestProcess(List<String> urls,
      RequestResponseProcess requestResponseProcess,
      RequestInterceptProcess requestProcess) {
    this.wrappedRequestProcess = requestProcess;
    this.requestResponseProcess = requestResponseProcess;
    this.urls = urls;
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.uri(request);
    if (null == urls) {
      return false;
    }
    for (String ignoreUrl: urls) {
      if (null != url && url.startsWith(ignoreUrl)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public <Q, R> void process(Q request, R response) throws Exception {
    wrappedRequestProcess.process(request, response);
  }

}
