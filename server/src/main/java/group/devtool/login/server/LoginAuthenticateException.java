package group.devtool.login.server;

import group.devtool.login.client.LoginException;

/**
 * 登录认证异常
 */
public class LoginAuthenticateException extends LoginException {

  private static final long serialVersionUID = -4493661893600535442L;

  public LoginAuthenticateException(String message) {
    super(message);
  }

}
