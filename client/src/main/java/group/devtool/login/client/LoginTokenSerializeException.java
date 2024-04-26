package group.devtool.login.client;

public class LoginTokenSerializeException extends LoginException {

  private static final long serialVersionUID = -149217994734189756L;

  public LoginTokenSerializeException(String reason) {
    super("登录凭证序列化异常，原因：" + reason);
  }


}
