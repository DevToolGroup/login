package group.devtool.login.client;

public class LoginApplicationTest implements LoginApplication {

  private static final long serialVersionUID = -6320822382762674064L;

  private String originUrl;
  
  private String appId;

  public LoginApplicationTest(String originUrl, String appId) {
    this.originUrl = originUrl;
    this.appId = appId;
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
