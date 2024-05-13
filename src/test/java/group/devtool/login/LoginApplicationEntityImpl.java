package group.devtool.login;


public class LoginApplicationEntityImpl {

  private String appId;

  private String redirectUrl;

  public String getAppId() {
    return appId;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public void setRedirectUrl(String originUrl) {
    this.redirectUrl = originUrl;
  }

  

}
