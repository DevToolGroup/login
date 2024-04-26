package group.devtool.login.server;

import group.devtool.login.client.RequestInterceptProcess;
import group.devtool.login.client.RequestResponseProcess;

public class DispatchServerRequestProcess extends DispatchRequestProcess {

  private LoginServerProperties properties;

  public DispatchServerRequestProcess(RequestResponseProcess requestResponseProcess,
      RequestInterceptProcess... processes) {
    super(requestResponseProcess, processes);
    properties = LoginServerConfiguration.properties();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = getRequestResponseProcess().url(request);
    return url.startsWith(properties.loginContextPath());
  }
}
