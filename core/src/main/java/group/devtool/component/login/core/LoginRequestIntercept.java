package group.devtool.component.login.core;

import group.devtool.component.login.core.exception.LoginRuntimeException;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;

import java.util.Iterator;
import java.util.List;

/**
 * 登录拦截器，整体程序的核心入口
 */
public interface LoginRequestIntercept extends RequestResponseProcess {

  /**
   * 请求处理器
   * 
   * @param requestProcess 实际业务处理器
   * @return 请求处理器列表
   */
  List<RequestInterceptProcess> loginInterceptProcesses(RequestInterceptProcess requestProcess);

  /**
   * 拦截用户请求，判断是否是单点登录相关请求并处理登录的状态
   * 
   * @param <Q>            请求实际类型
   * @param <R>            响应实际类型
   * @param request        用户请求
   * @param response       用户响应
   * @param requestProcess 业务请求处理
   * @throws Exception 处理异常
   */
  default <Q, R> void intercept(Q request, R response, RequestInterceptProcess requestProcess) throws Exception {
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
      throw new LoginRuntimeException("未找到匹配的处理器");
    }
  }

}
