package group.devtool.login.server;

import group.devtool.login.client.LoginAuthorization;

/**
 * 登录认证结果
 */
public class LoginAuthorizationResult {

  private String originUrl;

  private LoginAuthorization authorization;

  public LoginAuthorizationResult(String originUrl, LoginAuthorization authorization) {
    this.originUrl = originUrl;
    this.authorization = authorization;
  }

  /**
   * 应用的访问地址
   * 
   * @return
   */
  public String originUrl() {
    return originUrl;
  }

  /**
   * 登录实体信息
   * 
   * @return
   */
  public LoginAuthorization authorization() {
    return authorization;
  }

}
