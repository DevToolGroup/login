package group.devtool.login;

/**
 * 登录凭证限制异常
 */
public class LoginRestrictException extends LoginException {

  private static final long serialVersionUID = -7850459254908401503L;

  /**
   * 登录凭证限制
   * 
   * @param message 限制原因
   */
  public LoginRestrictException(String message) {
    super(message);
  }

}
