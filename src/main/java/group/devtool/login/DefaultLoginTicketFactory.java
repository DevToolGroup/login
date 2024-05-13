package group.devtool.login;


/**
 * 登录凭证
 */
public class DefaultLoginTicketFactory implements LoginTicketFactory {

  @Override
  public LoginTicket create(String ticketId,
      LoginAuthorization authorization,
      LoginService application,
      TokenExpirePolicy expirePolicy) {
    return new DefaultLoginTicket(ticketId,
        authorization,
        application,
        expirePolicy);
  }

}
