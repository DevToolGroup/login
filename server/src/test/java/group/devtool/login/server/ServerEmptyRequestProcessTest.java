package group.devtool.login.server;

import group.devtool.login.client.RequestInterceptProcess;

public class ServerEmptyRequestProcessTest implements RequestInterceptProcess{

  @Override
  public <Q, R> void process(Q request, R response) {
    TestServerResponse testResponse = (TestServerResponse) response;
    testResponse.success("empty");
  }
  
}
