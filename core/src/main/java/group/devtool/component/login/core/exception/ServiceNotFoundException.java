package group.devtool.component.login.core.exception;

/**
 * 应用不存在
 */
public class ServiceNotFoundException extends LoginException {

  private static final long serialVersionUID = 1L;

  /**
   * 应用不存在
   * 
   * @param message 应用参数信息
   */
  public ServiceNotFoundException(String message) {
    super(message);
  }

}
