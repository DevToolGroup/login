package group.devtool.login;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * 登录服务端请求分发器，区分服务端登录请求，业务请求
 */
public abstract class DispatchRequestProcess implements RequestInterceptProcess {

  private List<RequestInterceptProcess> processes;

  private RequestResponseProcess requestResponseProcess;

  /**
   * 初始化请求分发器
   * 
   * @param requestResponseProcess 请求响应处理器
   * @param processes              请求处理器
   */
  public DispatchRequestProcess(RequestResponseProcess requestResponseProcess,
      RequestInterceptProcess... processes) {
    if (processes.length < 1) {
      throw new IllegalArgumentException("请求处理器数量不能为空");
    }
    this.processes = Arrays.asList(processes);
    this.requestResponseProcess = requestResponseProcess;
  }

  /**
   * @return 请求响应处理器
   */
  protected RequestResponseProcess getRequestResponseProcess() {
    return requestResponseProcess;
  }

  @Override
  public <Q, R> void process(Q request, R response) throws Exception {
    boolean matched = false;
    Iterator<RequestInterceptProcess> iterator = processes.iterator();
    do {
      RequestInterceptProcess process = iterator.next();
      if (process.match(request)) {
        process.process(request, response);
        matched = true;
      }
    } while (iterator.hasNext() && !matched);
    if (!matched) {
      throw new LoginUnknownException("未找到匹配的处理器");
    }
  }

}
