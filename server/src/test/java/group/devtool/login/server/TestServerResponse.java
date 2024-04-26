package group.devtool.login.server;

import group.devtool.login.client.LoginCookie;
import group.devtool.login.client.LoginException;

public class TestServerResponse {

  private LoginException error;

  private String redirectUrl;

  private LoginCookie[] cookies;

  private Object entity;

  
  public LoginException getError() {
    return error;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public LoginCookie[] getCookies() {
    return cookies;
  }

  public Object getEntity() {
    return entity;
  }

  public void error(LoginException e) {
    this.error = e;
  }

  public void redirect(String redirectUrl, LoginCookie... cookies) {
    this.redirectUrl = redirectUrl;
    this.cookies = cookies;
  }

  public <T> void success(T entity, LoginCookie... cookies) {
    this.entity = entity;
    this.cookies = cookies;
  }
  
}
