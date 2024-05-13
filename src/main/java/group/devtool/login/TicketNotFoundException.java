package group.devtool.login;

/**
 * 登录凭证不存在
 */
public class TicketNotFoundException extends LoginException {

  private static final long serialVersionUID = 3005431347764350570L;

  /**
   * 登录凭证不存在
   * 
   * @param message 异常信息
   */
  public TicketNotFoundException(String message) {
    super(message);
  }

}
