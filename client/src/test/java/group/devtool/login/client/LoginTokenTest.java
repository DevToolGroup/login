package group.devtool.login.client;

public class LoginTokenTest implements LoginToken {

  private static final long serialVersionUID = -4106576401380588137L;

  private String id;

  private LoginAuthorization authorization;
  
  private LoginApplication application;

  public LoginTokenTest(String id, LoginAuthorization authorization, LoginApplication application) {
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
