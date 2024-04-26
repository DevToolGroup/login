package group.devtool.login.client;

public class EmptyRequestProcessTest implements RequestInterceptProcess{

  @Override
  public <Q, R> void process(Q request, R response) {
    TestResponse testResponse = (TestResponse) response;
    testResponse.success("empty");
  }
  
}
