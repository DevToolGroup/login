package group.devtool.login;

/**
 * 默认应用登录凭证请求
 */
public class DefaultApplicationTicketRequest implements RedirectApplicationTicketRequest {

  private String appTicketId;

  /**
   * 根据应用登录凭证标识构建默认应用登录凭证请求
   * 
   * @param appTicketId 应用登录凭证标识
   */
  public DefaultApplicationTicketRequest(String appTicketId) {
    this.appTicketId = appTicketId;
  }

  @Override
  public String applicationTicketId() {
    return appTicketId;
  }

}
