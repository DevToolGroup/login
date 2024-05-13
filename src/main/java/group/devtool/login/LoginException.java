package group.devtool.login;

/**
 * 统一登录异常
 */
public class LoginException extends Exception {

  private static final long serialVersionUID = 3819381685651789614L;

  /**
   * 统一登录异常
   * 
   * @param message 异常信息
   */
  public LoginException(String message) {
    super(message);
  }

}
