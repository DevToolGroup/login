package group.devtool.component.login.server.protocol.response.impl;

import group.devtool.component.login.core.exception.LoginRuntimeException;
import group.devtool.component.login.server.protocol.response.RedirectAuthenticateResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 默认实现，认证重定向响应
 */
public class DefaultRedirectAuthenticateResponse implements RedirectAuthenticateResponse {

  private static final String REDIRECT_URL = "redirectUrl";

  private static final String ARGUMENT_APPID = "appid";

  private final String authenticateUrl;

  private final String appId;

  private final String redirectUrl;

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
      return authenticateUrl +
              "?" +
              ARGUMENT_APPID +
              "=" +
              appId +
              "&" +
              REDIRECT_URL +
              "=" +
              URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new LoginRuntimeException("URL编码失败，原因：" + e.getMessage());
    }
  }

}
