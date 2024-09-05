package group.devtool.component.login.core.exception;

/**
 * 应用登录授权参数异常
 */
public class InvalidLoginAuthorizeParameterException extends LoginException {

  private static final long serialVersionUID = -6807586385874787084L;

  /**
   * 应用登录授权参数异常
   * @param param 授权参数
   */
  public InvalidLoginAuthorizeParameterException(String param) {
    super("无效的授权参数，参数内容：" + param);
  }

}
