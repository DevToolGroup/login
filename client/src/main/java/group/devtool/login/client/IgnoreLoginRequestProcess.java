package group.devtool.login.client;

import java.util.List;

/**
 * 忽略登录状态的业务请求处理器
 */
public class IgnoreLoginRequestProcess implements RequestInterceptProcess {

  private List<String> urls;

  private RequestInterceptProcess wrappedRequestProcess;

  private RequestResponseProcess requestResponseProcess;

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
    for (String ignoreUrl: urls) {
      if (null != url && url.startsWith(ignoreUrl)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    wrappedRequestProcess.process(request, response);
  }

}
