package group.devtool.login.server;

import java.util.Map;

public class TestServerRequest {

  private String uri;

  private String url;

  private String logoutRedirectUrl;

  private Map<String, String> cookies;

  public void setUri(String uri) {
    this.uri = uri;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setLogoutRedirectUrl(String logoutRedirectUrl) {
    this.logoutRedirectUrl = logoutRedirectUrl;
  }

  public void setCookies(Map<String, String> cookies) {
    this.cookies = cookies;
  }

  public String uri() {
    return uri;
  }

  public String url() {
    return url;
  }

  public String logoutRedirectUrl() {
    return logoutRedirectUrl;
  }

  public String cookie(String name) {
    return cookies.get(name);
  }
  
}
