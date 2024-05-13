package group.devtool.login;

/**
 * 登录认证异常
 */
public class LoginAuthenticateException extends LoginException {

  private static final long serialVersionUID = -4493661893600535442L;

  /**
   * 认证异常
   * 
   * @param message 异常信息
   */
  public LoginAuthenticateException(String message) {
    super(message);
  }

}
