package group.devtool.component.login.core.exception;

/**
 * 登录相关参数异常
 */
public class LoginParameterIllegalException extends LoginRuntimeException {

  private static final long serialVersionUID = -3960186245733180834L;

  /**
   * 登录相关参数异常
   * 
   * @param message 异常信息
   */
  public LoginParameterIllegalException(String message) {
    super(message);
  }

}
