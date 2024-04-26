package group.devtool.login.server;

import group.devtool.login.client.LoginException;

/**
 * 登录凭证限制异常
 */
public class LoginRestrictException extends LoginException {

  private static final long serialVersionUID = -7850459254908401503L;

  public LoginRestrictException(String message) {
    super(message);
  }

}
