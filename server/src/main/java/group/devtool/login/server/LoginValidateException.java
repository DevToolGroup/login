package group.devtool.login.server;

import group.devtool.login.client.LoginException;

/**
 * 登录校验异常
 */
public class LoginValidateException extends LoginException {

  private static final long serialVersionUID = 7209101438883241668L;

  public LoginValidateException(String message) {
    super(message);
  }

}
