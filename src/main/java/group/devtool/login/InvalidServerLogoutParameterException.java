package group.devtool.login;

/**
 * 服务端登出请求参数异常
 */
public class InvalidServerLogoutParameterException extends LoginException {

  private static final long serialVersionUID = 1644997854409694226L;

  /**
   * 服务端登出请求参数异常
   * 
   * @param url 请求地址
   */
  public InvalidServerLogoutParameterException(String url) {
    super("服务端登出参数异常，路径地址：" + url);
  }

}
