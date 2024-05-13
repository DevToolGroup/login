package group.devtool.login;

/**
 * 应用登录凭证校验请求
 */
public interface ServerTicketValidateRequest extends LoginProtocolRequest {

  /**
   * 
   * @return 应用凭证ID
   */
  public String appTicketId();

  /**
   * 
   * @return 应用凭证ID签名
   */
  public String signAppTicketId();

}
