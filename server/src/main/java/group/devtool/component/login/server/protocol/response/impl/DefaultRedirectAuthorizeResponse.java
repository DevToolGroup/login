package group.devtool.component.login.server.protocol.response.impl;

import group.devtool.component.login.core.exception.LoginRuntimeException;
import group.devtool.component.login.core.service.LoginService;
import group.devtool.component.login.server.protocol.response.RedirectAuthorizeResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 默认实现，授权重定向响应
 */
public class DefaultRedirectAuthorizeResponse implements RedirectAuthorizeResponse {

  private static final String REDIRECT = "redirectUrl";

  private static final String APPID = "appid";

  private final String authorizeUrl;

  private final LoginService application;

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
      return authorizeUrl +
              "?" +
              APPID +
              "=" +
              application.appId() +
              "&" +
              REDIRECT +
              "=" +
              URLEncoder.encode(application.redirectUrl(), StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new LoginRuntimeException(e.getMessage());
    }
  }

}
