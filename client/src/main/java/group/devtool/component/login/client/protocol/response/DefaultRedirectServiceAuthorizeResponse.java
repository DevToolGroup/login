package group.devtool.component.login.client.protocol.response;

import group.devtool.component.login.core.exception.LoginRuntimeException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 应用授权重定向响应的默认实现
 */
public class DefaultRedirectServiceAuthorizeResponse implements RedirectServiceAuthorizeResponse {

  private static final String REDIRECT_URL = "redirectUrl";

  private static final String ARGUMENT_APPID = "appid";

  private final String serverAuthorizeUrl;

  private final String appId;

  private final String loggedRedirectUrl;

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
      return serverAuthorizeUrl +
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
