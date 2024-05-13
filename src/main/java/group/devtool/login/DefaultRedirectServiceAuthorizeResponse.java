package group.devtool.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 应用授权重定向响应的默认实现
 */
public class DefaultRedirectServiceAuthorizeResponse implements RedirectServiceAuthorizeResponse {

  private static final String REDIRECT = "redirectUrl";

  private static final String APPID = "appid";

  private String serverAuthorizeUrl;

  private String appId;

  private String loggedRedirectUrl;

  /**
   * 应用授权重定向响应
   * 
   * @param serverAuthorizeUrl 登录服务授权地址
   * @param appId 应用appId
   * @param loggedRedirectUrl 登录重定向地址
   */
  public DefaultRedirectServiceAuthorizeResponse(String serverAuthorizeUrl, String appId, String loggedRedirectUrl) {
    this.serverAuthorizeUrl = serverAuthorizeUrl;
    this.appId = appId;
    this.loggedRedirectUrl = loggedRedirectUrl;
  }

  @Override
  public String getLocation() {
    try {
      return new StringBuilder()
          .append(serverAuthorizeUrl)
          .append("?")
          .append(APPID)
          .append("=")
          .append(appId)
          .append("&")
          .append(REDIRECT)
          .append("=")
          .append(URLEncoder.encode(loggedRedirectUrl, StandardCharsets.UTF_8.name()))
          .toString();
    } catch (UnsupportedEncodingException e) {
      throw new LoginUnknownException("URL编码失败，原因：" + e.getMessage());
    }
  }

}
