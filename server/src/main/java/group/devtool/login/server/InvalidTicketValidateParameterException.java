package group.devtool.login.server;

import group.devtool.login.client.LoginException;

public class InvalidTicketValidateParameterException extends LoginException {

  private static final long serialVersionUID = -2771530679825829266L;

  public InvalidTicketValidateParameterException(String message) {
    super("应用登录凭证校验参数异常，链接：" + message);
  }

}
