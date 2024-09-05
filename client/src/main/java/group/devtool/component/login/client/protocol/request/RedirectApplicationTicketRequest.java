package group.devtool.component.login.client.protocol.request;

import group.devtool.component.login.core.protocol.LoginProtocolRequest;

/**
 * 应用登录凭证重定向请求
 */
public interface RedirectApplicationTicketRequest extends LoginProtocolRequest {

  /**
   * @return 应用登录凭证唯一标识
   */
  String applicationTicketId();

}
