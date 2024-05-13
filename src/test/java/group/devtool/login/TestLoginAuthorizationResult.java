package group.devtool.login;

public class TestLoginAuthorizationResult implements LoginAuthorizationResult {

  private LoginAuthorization authorization;

  private LoginService application;

  public TestLoginAuthorizationResult(LoginAuthorization authorization, LoginService application) {
    this.authorization = authorization;
    this.application = application;
  }

  @Override
  public LoginAuthorization authorization() {
    return authorization;
  }

  @Override
  public LoginService loginApplication() {
    return application;
  }

}
