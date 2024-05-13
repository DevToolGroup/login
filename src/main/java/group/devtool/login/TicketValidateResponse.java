package group.devtool.login;

/**
 * 凭证验证响应
 */
public interface TicketValidateResponse extends LoginProtocolResponse {

  /**
   * 
   * @return 登录状态
   */
  public LoginToken getToken();
}
