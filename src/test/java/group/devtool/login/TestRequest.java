package group.devtool.login;

import java.util.Map;

public class TestRequest {

  private String uri;

  private String url;

  private String logoutRedirectUrl;

  private Map<String, String> cookies;

  private Map<String, String> queries;

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

  public Map<String, String> getCookies() {
    return cookies;
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

  public Map<String, String> query() {
    return queries;
  }

  public void setQuery(Map<String, String> queries) {
    this.queries = queries;
  }
  
}
