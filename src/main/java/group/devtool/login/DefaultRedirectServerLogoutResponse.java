package group.devtool.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 登录服务退出重定向默认实现
 */
public class DefaultRedirectServerLogoutResponse implements RedirectServerLogoutResponse {

  private static final String AppId = "appid";

  private static final String RedirectUrl = "redirectUrl";

  private String serverLogoutUrl;

  private String appId;

  private String loggedRedirectUrl;

  /**
   * 登录服务退出重定向默认实现
   * 
   * @param serverLogoutUrl 登录服务的登出地址
   * @param appId 登出应用的appId
   * @param loggedRedirectUrl 再次登录的重定向地址
   */
  public DefaultRedirectServerLogoutResponse(String serverLogoutUrl, String appId, String loggedRedirectUrl) {
    this.serverLogoutUrl = serverLogoutUrl;
    this.appId = appId;
    this.loggedRedirectUrl = loggedRedirectUrl;
  }

  @Override
  public String getLocation() {
    try {
      return new StringBuilder()
          .append(serverLogoutUrl)
          .append("?")
          .append(AppId)
          .append("=")
          .append(appId)
          .append("&")
          .append(RedirectUrl)
          .append("=")
          .append(URLEncoder.encode(loggedRedirectUrl, StandardCharsets.UTF_8.name()))
          .toString();
    } catch (UnsupportedEncodingException e) {
      throw new LoginUnknownException("URL编码失败，原因：" + e.getMessage());
    }
  }

}
