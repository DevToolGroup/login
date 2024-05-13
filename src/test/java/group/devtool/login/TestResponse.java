package group.devtool.login;

public class TestResponse {

  private LoginException error;

  private String redirectUrl;

  private LoginCookie[] cookies;

  private Object entity;

  
  public LoginException getError() {
    return error;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public LoginCookie cookie(String name) {
    if (null == cookies) {
      return null;
    }
    for (LoginCookie cookie: cookies) {
      if (cookie.getName().equals(name)) {
        return cookie;
      }
    }
    return null;
  }

  public LoginCookie[] getCookies() {
    return cookies;
  }

  public Object getEntity() {
    return entity;
  }

  public void error(LoginException e) {
    this.error = e;
  }

  public void redirect(String redirectUrl, LoginCookie... cookies) {
    this.redirectUrl = redirectUrl;
    if (cookies.length == 0 || null == cookies) {
      return;
    }
    this.cookies = cookies;
  }

  public <T> void success(T entity, LoginCookie... cookies) {
    this.entity = entity;
    if (cookies.length == 0 || null == cookies) {
      return;
    }
    this.cookies = cookies;
  }
  
}
