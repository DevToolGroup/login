package group.devtool.component.login.core.exception;

/**
 * 运行时为止异常基类
 */
public class LoginRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 8531738351603975921L;

  /**
   * 运行时为止异常基类
   * 
   * @param message 异常信息
   */
  public LoginRuntimeException(String message) {
    super(message);
  }

}
