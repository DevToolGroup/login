package group.devtool.component.login.server.protocol.response.impl;

import group.devtool.component.login.server.protocol.response.RedirectServiceTicketResponse;

/**
 * 默认实现，应用登录凭证重定向响应
 */
public class DefaultRedirectServiceTicketResponse implements RedirectServiceTicketResponse {

  private final String applicationTicketUrl;

  private final String appTicketId;

  /**
   * 初始化应用登录凭证重定向响应
   * 
   * @param applicationTicketUrl 应用登录凭证接收地址
   * @param appTicketId          应用登录凭证唯一标识
   */
  public DefaultRedirectServiceTicketResponse(String applicationTicketUrl, String appTicketId) {
    this.applicationTicketUrl = applicationTicketUrl;
    this.appTicketId = appTicketId;
  }

  @Override
  public String getLocation() {
    return applicationTicketUrl +
            "?appTicket=" +
            appTicketId;
  }

}
