package group.devtool.login.server;

import group.devtool.login.client.LoginAuthorization;

public class TestLoginAuthorization implements LoginAuthorization {

  private static final long serialVersionUID = 7099936398109336817L;

  private String id;

  public TestLoginAuthorization(String account) {
    this.id = account;
  }

  @Override
  public String id() {
    return id;
  }

}
