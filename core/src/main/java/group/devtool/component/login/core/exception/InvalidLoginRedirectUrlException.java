package group.devtool.component.login.core.exception;

/**
 * 应用登录跳转链接异常
 */
public class InvalidLoginRedirectUrlException extends LoginException {

  private static final long serialVersionUID = -6631573741240612699L;

  /**
   * 应用登录跳转链接异常
   * 
   * @param url 跳转链接
   */
  public InvalidLoginRedirectUrlException(String url) {
    super("无效的登录重定向地址");
  }


}
