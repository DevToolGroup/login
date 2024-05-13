package group.devtool.login;

import java.util.Map;

public class RequestResponseProcessTest implements RequestResponseProcess {

  @Override
  public <Q> String uri(Q request) {
    TestRequest testRequest = (TestRequest) request;
    return testRequest.uri();
  }

  @Override
  public <Q> String url(Q request) {
    TestRequest testRequest = (TestRequest) request;
    return testRequest.url();
  }

  @Override
  public <Q> String cookie(Q request, String name) {
    TestRequest testRequest = (TestRequest) request;
    return testRequest.cookie(name);
  }

  @Override
  public <Q> Map<String, String> query(Q request) {
    TestRequest testRequest = (TestRequest) request;
    return testRequest.query();
  }

  @Override
  public void saveLogin(LoginAuthorization authorization) {
    LoginContextTest.set(authorization);
  }

  @Override
  public void removeLogin(LoginAuthorization authorization) {
    LoginContextTest.clean();
  }

  @Override
  public <R> void error(R response, LoginException e) {
    TestResponse testResponse = (TestResponse) response;
    testResponse.error(e);
  }

  @Override
  public <R> void redirect(R response, LoginRedirectResponse redirectResponse, LoginCookie... cookies) {
    TestResponse testResponse = (TestResponse) response;
    testResponse.redirect(redirectResponse.getLocation(), cookies);
  }

  @Override
  public <R> void success(R response, LoginProtocolResponse protocolResponse, LoginCookie... cookies) {
    TestResponse testResponse = (TestResponse) response;
    testResponse.success(protocolResponse, cookies);
  }

}
