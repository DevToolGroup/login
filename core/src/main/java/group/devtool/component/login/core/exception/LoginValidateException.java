package group.devtool.component.login.core.exception;

/**
 * 登录校验异常
 */
public class LoginValidateException extends LoginException {

  private static final long serialVersionUID = 7209101438883241668L;

  /**
   * 登录校验异常
   * 
   * @param message 异常信息
   */
  public LoginValidateException(String message) {
    super(message);
  }

}
