package group.devtool.component.login.server.protocol.request;


import group.devtool.component.login.core.protocol.LoginProtocolRequest;

/**
 * 应用登录凭证验证请求接口
 */
public interface ClientTicketValidateRequest extends LoginProtocolRequest {

  /**
   * @return 应用凭证ID
   */
  public String appTicketId();

  /**
   * @return 应用凭证ID签名
   */
  public String signAppTicketId();
}
