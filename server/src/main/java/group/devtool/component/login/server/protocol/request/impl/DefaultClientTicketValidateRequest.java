package group.devtool.component.login.server.protocol.request.impl;

import group.devtool.component.login.server.protocol.request.ClientTicketValidateRequest;

/**
 * 应用登录凭证校验请求默认实现
 */
public class DefaultClientTicketValidateRequest implements ClientTicketValidateRequest {

  private final String applicationTicketId;

  private final String signApplicationTicketId;

  /**
   * 初始化应用登录凭证校验请求
   * 
   * @param applicationTicketId     应用登录凭证ID
   * @param signApplicationTicketId 应用登录凭证ID签名
   */
  public DefaultClientTicketValidateRequest(String applicationTicketId, String signApplicationTicketId) {
    this.applicationTicketId = applicationTicketId;
    this.signApplicationTicketId = signApplicationTicketId;
  }

  @Override
  public String appTicketId() {
    return applicationTicketId;
  }

  @Override
  public String signAppTicketId() {
    return signApplicationTicketId;
  }

}
