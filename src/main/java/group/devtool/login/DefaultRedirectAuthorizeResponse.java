package group.devtool.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 默认实现，授权重定向响应
 */
public class DefaultRedirectAuthorizeResponse implements RedirectAuthorizeResponse {

  private static final String REDIRECT = "redirectUrl";

  private static final String APPID = "appid";

  private String authorizeUrl;

  private LoginService application;

  /**
   * 初始化授权响应
   * 
   * @param authorizeUrl 授权地址
   * @param application  登录应用
   */
  public DefaultRedirectAuthorizeResponse(String authorizeUrl, LoginService application) {
    this.authorizeUrl = authorizeUrl;
    this.application = application;
  }

  @Override
  public String getLocation() {
    try {
      return new StringBuilder()
          .append(authorizeUrl)
          .append("?")
          .append(APPID)
          .append("=")
          .append(application.appId())
          .append("&")
          .append(REDIRECT)
          .append("=")
          .append(URLEncoder.encode(application.redirectUrl(), StandardCharsets.UTF_8.name()))
          .toString();
    } catch (UnsupportedEncodingException e) {
      throw new LoginUnknownException(e.getMessage());
    }
  }

}
