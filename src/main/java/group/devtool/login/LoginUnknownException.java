package group.devtool.login;

/**
 * 未知错误异常
 */
public class LoginUnknownException extends LoginRuntimeException {

  private static final long serialVersionUID = 3819381685651789614L;

  /**
   * 未知错误异常 
   * 
   * @param message 异常信息
   */
  public LoginUnknownException(String message) {
    super(message);
  }

}
