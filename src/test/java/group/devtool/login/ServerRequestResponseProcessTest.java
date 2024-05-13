package group.devtool.login;

import java.util.Map;

public class ServerRequestResponseProcessTest implements RequestResponseProcess {

  @Override
  public <Q> String uri(Q request) {
    TestServerRequest testRequest = (TestServerRequest) request;
    return testRequest.uri();
  }

  @Override
  public <Q> String url(Q request) {
    TestServerRequest testRequest = (TestServerRequest) request;
    return testRequest.url();
  }

  @Override
  public <Q> String cookie(Q request, String name) {
    TestServerRequest testRequest = (TestServerRequest) request;
    return testRequest.cookie(name);
  }

  @Override
  public void saveLogin(LoginAuthorization authorization) {
    ServerLoginContextTest.set(authorization);
  }

  @Override
  public void removeLogin(LoginAuthorization authorization) {
    ServerLoginContextTest.clean();
  }

  @Override
  public <R> void error(R response, LoginException e) {
    TestServerResponse testResponse = (TestServerResponse) response;
    testResponse.error(e);
  }

  @Override
  public <Q> Map<String, String> query(Q request) {
    TestServerRequest testRequest = (TestServerRequest) request;
    return testRequest.query();
  }

  @Override
  public <R> void redirect(R response, LoginRedirectResponse redirectResponse, LoginCookie... cookies) {
    TestServerResponse testResponse = (TestServerResponse) response;
    testResponse.redirect(redirectResponse.getLocation(), cookies);
  }

  @Override
  public <R> void success(R response, LoginProtocolResponse protocolResponse, LoginCookie... cookies) {
    TestServerResponse testResponse = (TestServerResponse) response;
    testResponse.success(protocolResponse, cookies);
  }

}
