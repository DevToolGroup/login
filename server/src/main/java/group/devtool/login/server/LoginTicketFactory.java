package group.devtool.login.server;

import group.devtool.login.client.TokenExpirePolicy;
import group.devtool.login.client.LoginApplication;
import group.devtool.login.client.LoginAuthorization;

/**
 * 登录凭证构造器
 */
public interface LoginTicketFactory {

  LoginTicket create(String ticketId,
      LoginAuthorization authorization,
      LoginApplication application,
      TokenExpirePolicy expirePolicy);

}
