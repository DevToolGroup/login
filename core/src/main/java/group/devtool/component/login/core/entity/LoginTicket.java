package group.devtool.component.login.core.entity;

import group.devtool.component.login.core.TokenExpirePolicy;
import group.devtool.component.login.core.service.LoginService;

import java.util.Map;

/**
 * 登录凭证
 */
public interface LoginTicket extends Ticket, LoginToken {

  /**
   * 已登录应用
   * 
   * @return 已登录应用及其对应的登录ID
   */
  Map<String, LoginService> loggedApplication();

  /**
   * 生成应用登录凭证
   * 
   * @param ticketId     登录凭证唯一约束
   * @param application  登录应用
   * @param expirePolicy 凭证过期策略
   * @return 应用登录授权码
   */
  ServiceTicket createTicket(String ticketId,
      LoginService application,
      TokenExpirePolicy expirePolicy);

}
