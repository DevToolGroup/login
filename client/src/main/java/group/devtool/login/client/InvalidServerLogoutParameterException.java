package group.devtool.login.client;

public class InvalidServerLogoutParameterException extends LoginException {

  private static final long serialVersionUID = 1644997854409694226L;

  public InvalidServerLogoutParameterException(String url) {
    super("服务端登出参数异常，路径地址：" + url);
  }

}
