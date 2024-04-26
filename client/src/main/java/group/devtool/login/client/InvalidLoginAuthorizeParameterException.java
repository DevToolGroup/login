package group.devtool.login.client;

public class InvalidLoginAuthorizeParameterException extends LoginException {

  private static final long serialVersionUID = -6807586385874787084L;

  public InvalidLoginAuthorizeParameterException(String param) {
    super("无效的授权参数，参数内容：" + param);
  }

}
