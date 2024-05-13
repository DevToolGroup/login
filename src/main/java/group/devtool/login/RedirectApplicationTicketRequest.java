package group.devtool.login;

/**
 * 应用登录凭证重定向请求
 */
public interface RedirectApplicationTicketRequest extends LoginProtocolRequest {

  /**
   * @return 应用登录凭证唯一标识
   */
  String applicationTicketId();

}
