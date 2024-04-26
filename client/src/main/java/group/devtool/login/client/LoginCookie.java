package group.devtool.login.client;

public final class LoginCookie {

  private String name;

  private String value;
  
  private boolean httpOnly;

  public LoginCookie(String name, String value, boolean httpOnly) {
    this.name = name;
    this.value = value;
    this.httpOnly = httpOnly;
  }

  public String getName() {
    return name;
  }

  public String getValue() {
    return value;
  }

  public boolean isHttpOnly() {
    return httpOnly;
  }

}
