package group.devtool.login;

public class ServerEmptyRequestProcessTest implements RequestInterceptProcess{

  @Override
  public <Q, R> void process(Q request, R response) {
    TestServerResponse testResponse = (TestServerResponse) response;
    testResponse.success("empty");
  }
  
}
