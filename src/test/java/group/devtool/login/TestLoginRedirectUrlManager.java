package group.devtool.login;

public class TestLoginRedirectUrlManager implements LoginRedirection {

  private String loggedRedirectUrl;

  private String logoutRedirectUrl;

  public TestLoginRedirectUrlManager(String loggedRedirectUrl, String logoutRedirectUrl) {
    this.loggedRedirectUrl = loggedRedirectUrl;
    this.logoutRedirectUrl = logoutRedirectUrl;
  }

  @Override
  public <Q> String loggedRedirectUrl(Q request) {
    return loggedRedirectUrl;
  }

  @Override
  public <Q> String logoutRedirectUrl(Q request) {
    return logoutRedirectUrl;
  }

}
