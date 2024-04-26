package group.devtool.login.client;

import java.util.Iterator;
import java.util.List;

public interface LoginRequestIntercept extends RequestResponseProcess {

  List<RequestInterceptProcess> loginInterceptProcesses(RequestInterceptProcess requestProcess);

  /**
   * 拦截用户请求，判断是否是单点登录相关请求并处理登录的状态
   * 
   * @param request        用户请求
   * @param requestProcess 业务请求处理
   * @return 请求结果
   * @throws Throwable 处理异常
   */
  default <Q, R> void intercept(Q request, R response, RequestInterceptProcess requestProcess) throws Throwable {
    List<RequestInterceptProcess> processes = loginInterceptProcesses(requestProcess);
    Iterator<RequestInterceptProcess> iterator = processes.iterator();

    boolean matched = false;
    do {
      RequestInterceptProcess process = iterator.next();
      if (process.match(request)) {
        matched = true;
        process.process(request, response);
      }
    } while (iterator.hasNext() && !matched);
    if (!matched) {
      throw new LoginUnknownException("未找到匹配的处理器");
    }
  }

}
