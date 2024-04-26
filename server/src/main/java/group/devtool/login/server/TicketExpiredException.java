package group.devtool.login.server;

import group.devtool.login.client.LoginException;

/**
 * 登录凭证过期异常
 */
public class TicketExpiredException extends LoginException {

  private static final long serialVersionUID = 1663689597544250873L;

  public TicketExpiredException(String message) {
    super(message);
  }

}
