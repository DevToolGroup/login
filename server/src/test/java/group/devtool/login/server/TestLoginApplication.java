package group.devtool.login.server;

import group.devtool.login.client.LoginApplication;

public class TestLoginApplication implements LoginApplication {

  private static final long serialVersionUID = 2949647924867631734L;

  private String appId;

  private String originUrl;

  public TestLoginApplication(String appId, String originUrl) {
    this.appId = appId;
    this.originUrl = originUrl;
  }

  @Override
  public String appId() {
    return appId;
  }

  @Override
  public String originUrl() {
    return originUrl;
  }

}
