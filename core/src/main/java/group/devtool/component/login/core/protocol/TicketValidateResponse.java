package group.devtool.component.login.core.protocol;

import group.devtool.component.login.core.entity.LoginToken;

/**
 * 凭证验证响应
 */
public interface TicketValidateResponse extends LoginProtocolResponse {

  /**
   * 
   * @return 登录状态
   */
  LoginToken getToken();
}
