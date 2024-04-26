package group.devtool.login.client;

public class LoginAuthorizationTest implements LoginAuthorization {

  private static final long serialVersionUID = -2009668898943100307L;
  
  private String id;

  public LoginAuthorizationTest(String id) {
    this.id = id;
  }

  @Override
  public String id() {
    return id;
  }

}
