package group.devtool.component.login.core;

import group.devtool.component.login.core.entity.LoginAuthorization;
import group.devtool.component.login.core.entity.LoginTicket;
import group.devtool.component.login.core.service.LoginService;

/**
 * 登录凭证构造器
 */
public interface LoginTicketFactory {

  /**
   * 创建登录凭证
   * 
   * @param ticketId      登录凭证ID
   * @param authorization 认证结果
   * @param application   登录应用
   * @param expirePolicy  过期策略
   * @return 登录凭证
   */
  LoginTicket create(String ticketId,
                     LoginAuthorization authorization,
                     LoginService application,
                     TokenExpirePolicy expirePolicy);

}
