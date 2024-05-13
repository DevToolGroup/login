package group.devtool.login;

/**
 * 登录凭证过期异常
 */
public class TicketExpiredException extends LoginException {

  private static final long serialVersionUID = 1663689597544250873L;

  /**
   * 初始化登录凭证过期异常
   * @param message 异常信息
   */
  public TicketExpiredException(String message) {
    super(message);
  }

}
