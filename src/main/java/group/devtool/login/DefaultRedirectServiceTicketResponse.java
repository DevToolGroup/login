package group.devtool.login;

/**
 * 默认实现，应用登录凭证重定向响应
 */
public class DefaultRedirectServiceTicketResponse implements RedirectServiceTicketResponse {

  private String applicationTicketUrl;

  private String appTicketId;

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
    return new StringBuilder()
        .append(applicationTicketUrl)
        .append("?appTicket=")
        .append(appTicketId)
        .toString();
  }

}
