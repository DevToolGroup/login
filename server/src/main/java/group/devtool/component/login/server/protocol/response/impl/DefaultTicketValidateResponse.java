package group.devtool.component.login.server.protocol.response.impl;


import group.devtool.component.login.core.entity.LoginToken;
import group.devtool.component.login.core.entity.DefaultLoginToken;
import group.devtool.component.login.core.protocol.TicketValidateResponse;

/**
 * 应用凭证校验响应默认实现类
 */
public class DefaultTicketValidateResponse implements TicketValidateResponse {

  private final DefaultLoginToken token;

  /**
   * 初始化应用凭证校验响应
   * 
   * @param token 登录状态
   */
  public DefaultTicketValidateResponse(LoginToken token) {
    this.token = (DefaultLoginToken)token;
  }

  @Override
  public DefaultLoginToken getToken() {
    return token;
  }

}
