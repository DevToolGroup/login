package group.devtool.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 默认实现，认证重定向响应
 */
public class DefaultRedirectAuthenticateResponse implements RedirectAuthenticateResponse {

  private static final String REDIRECT = "redirectUrl";

  private static final String APPID = "appid";

  private String authenticateUrl;

  private String appId;

  private String redirectUrl;

  /**
   * 认证重定向响应
   * 
   * @param authenticateUrl 登录服务认证处理地址
   * @param appId           应用appId
   * @param redirectUrl     应用登录后的重定向地址
   */
  public DefaultRedirectAuthenticateResponse(String authenticateUrl, String appId, String redirectUrl) {
    this.authenticateUrl = authenticateUrl;
    this.appId = appId;
    this.redirectUrl = redirectUrl;
  }

  @Override
  public String getLocation() {
    try {
      return new StringBuilder()
          .append(authenticateUrl)
          .append("?")
          .append(APPID)
          .append("=")
          .append(appId)
          .append("&")
          .append(REDIRECT)
          .append("=")
          .append(URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.name()))
          .toString();
    } catch (UnsupportedEncodingException e) {
      throw new LoginUnknownException("URL编码失败，原因：" + e.getMessage());
    }
  }

}
