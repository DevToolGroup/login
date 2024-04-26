package group.devtool.login.server;

import group.devtool.login.client.LoginApplication;
import group.devtool.login.client.LoginAuthorization;
import group.devtool.login.client.LoginToken;

public class TestLoginToken implements LoginToken {

  private static final long serialVersionUID = 6661080742627822116L;
  
  private String id;

  private LoginAuthorization authorization;
  
  private LoginApplication application;

  public TestLoginToken(String id, LoginAuthorization authorization, LoginApplication application) {
    this.id = id;
    this.authorization = authorization;
    this.application = application;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public boolean isExpired() {
    return false;
  }

  @Override
  public LoginAuthorization authorization() {
    return authorization;
  }

  @Override
  public LoginApplication application() {
    return application;
  }

}
