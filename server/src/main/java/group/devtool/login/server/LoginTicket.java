package group.devtool.login.server;

import java.util.Map;

import group.devtool.login.client.TokenExpirePolicy;
import group.devtool.login.client.LoginApplication;
import group.devtool.login.client.LoginToken;
import group.devtool.login.client.Ticket;

/**
 * 登录凭证
 */
public interface LoginTicket extends Ticket, LoginToken {

  /**
   * 已登录应用
   * 
   * @return 已登录应用及其对应的登录ID
   */
  Map<String, LoginApplication> loggedApplication();

  /**
   * 生成应用登录凭证
   * 
   * @param ticketId
   * @param appId        应用ID
   * @param expirePolicy
   * @return 应用登录授权码
   */
  ApplicationTicket createTicket(String ticketId,
      LoginApplication application,
      TokenExpirePolicy expirePolicy);


}
