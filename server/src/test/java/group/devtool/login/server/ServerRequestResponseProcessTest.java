package group.devtool.login.server;

import group.devtool.login.client.LoginAuthorization;
import group.devtool.login.client.LoginCookie;
import group.devtool.login.client.LoginException;
import group.devtool.login.client.RequestResponseProcess;

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
  public <Q> String logoutRedirectUrl(Q request) {
    TestServerRequest testRequest = (TestServerRequest) request;
    return testRequest.logoutRedirectUrl();
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
  public <R> void redirect(R response, String redirectUrl, LoginCookie... cookies) {
    TestServerResponse testResponse = (TestServerResponse) response;
    testResponse.redirect(redirectUrl, cookies);
  }

  @Override
  public <T, R> void success(R response, T entity, LoginCookie... cookies) {
    TestServerResponse testResponse = (TestServerResponse) response;
    testResponse.success(entity, cookies);
  }

}
