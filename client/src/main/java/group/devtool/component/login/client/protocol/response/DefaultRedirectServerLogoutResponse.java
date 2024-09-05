package group.devtool.component.login.client.protocol.response;

import group.devtool.component.login.core.exception.LoginRuntimeException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 登录服务退出重定向默认实现
 */
public class DefaultRedirectServerLogoutResponse implements RedirectServerLogoutResponse {

  private static final String ARGUMENT_APPID = "appid";

  private static final String REDIRECT_URL = "redirectUrl";

  private final String serverLogoutUrl;

  private final String appId;

  private final String loggedRedirectUrl;

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
      return serverLogoutUrl +
              "?" +
              ARGUMENT_APPID +
              "=" +
              appId +
              "&" +
              REDIRECT_URL +
              "=" +
              URLEncoder.encode(loggedRedirectUrl, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new LoginRuntimeException("URL编码失败，原因：" + e.getMessage());
    }
  }

}
