package group.devtool.login.server;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import group.devtool.login.client.RequestInterceptProcess;
import group.devtool.login.client.RequestResponseProcess;

public abstract class DispatchRequestProcess implements RequestInterceptProcess {

  private List<RequestInterceptProcess> processes;

  private RequestResponseProcess requestResponseProcess;

  public DispatchRequestProcess(RequestResponseProcess requestResponseProcess,
      RequestInterceptProcess... processes) {
    if (processes.length < 1) {
      throw new IllegalArgumentException("请求处理器数量不能为空");
    }
    this.processes = Arrays.asList(processes);
    this.requestResponseProcess = requestResponseProcess;
  }

  protected RequestResponseProcess getRequestResponseProcess() {
    return requestResponseProcess;
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    boolean matched = false;
    Iterator<RequestInterceptProcess> iterator = processes.iterator();
    do {
      RequestInterceptProcess process = iterator.next();
      if (process.match(request)) {
        process.process(request, response);
        matched = true;
      }
    } while (iterator.hasNext() || !matched);
  }

}
