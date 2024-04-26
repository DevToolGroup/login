package group.devtool.login.server;

import group.devtool.login.client.LoginException;

/**
 * 登录凭证不存在
 */
public class TicketNotFoundException extends LoginException {

  private static final long serialVersionUID = 3005431347764350570L;

  public TicketNotFoundException(String message) {
    super(message);
  }


}
