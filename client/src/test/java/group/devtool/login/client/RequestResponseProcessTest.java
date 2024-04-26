package group.devtool.login.client;

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
  public <Q> String logoutRedirectUrl(Q request) {
    TestRequest testRequest = (TestRequest) request;
    return testRequest.logoutRedirectUrl();
  }

  @Override
  public <Q> String cookie(Q request, String name) {
    TestRequest testRequest = (TestRequest) request;
    return testRequest.cookie(name);
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
  public <R> void redirect(R response, String redirectUrl, LoginCookie... cookies) {
    TestResponse testResponse = (TestResponse) response;
    testResponse.redirect(redirectUrl, cookies);
  }

  @Override
  public <T, R> void success(R response, T entity, LoginCookie... cookies) {
    TestResponse testResponse = (TestResponse) response;
    testResponse.success(entity, cookies);
  }

}
