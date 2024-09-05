package group.devtool.component.login.server.protocol.factory;


import group.devtool.component.login.core.LoginTicketFactory;
import group.devtool.component.login.core.TokenExpirePolicy;
import group.devtool.component.login.core.entity.LoginAuthorization;
import group.devtool.component.login.core.entity.LoginTicket;
import group.devtool.component.login.core.service.LoginService;
import group.devtool.component.login.server.DefaultLoginTicket;

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
