package group.devtool.login.client;

public class LoginClientProperties {

  public static final String PROPERTY = "login.client.properties";

  private String appId;

  private String privateKey;

  private String authorizeUrl;

  private String serverLogoutUrl;

  private String clientLogoutUrl;

  private String validateUrl;

  private String appTicketUrl;

  /**
   * 客户端ID
   */
  public String appId() {
    return appId;
  }

  public void appId(String appId) {
    this.appId = appId;
  }

  /**
   * @return 客户端私钥
   */
  public String privateKey() {
    return privateKey;
  }

  public void privateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  /**
   * @return 授权登录地址，需要与服务端的授权登录地址匹配
   */
  public String authorizeUrl() {
    return authorizeUrl;
  }

  public void authorizeUrl(String authorizeUrl) {
    this.authorizeUrl = authorizeUrl;
  }

  /**
   * @return 服务端登出调用地址，需要与服务端登记的地址匹配
   */
  public String serverLogoutUrl() {
    return serverLogoutUrl;
  }

  public void serverLogoutUrl(String logoutUrl) {
    this.serverLogoutUrl = logoutUrl;
  }

  /**
   * @return 客户端登出地址
   */
  public String clientLogoutUrl() {
    return clientLogoutUrl;
  }

  public void clientLogoutUrl(String clientLogoutUrl) {
    this.clientLogoutUrl = clientLogoutUrl;
  }

  /**
   * @return 应用登录授权码验证地址，需要与服务端的验证地址保持一致
   */
  public String validateUrl() {
    return validateUrl;
  }

  public void validateUrl(String validateUrl) {
    this.validateUrl = validateUrl;
  }

  /**
   * 客户端接收applicationTicket的请求地址
   * @return
   */
  public String appTicketUrl() {
    return appTicketUrl;
  }

  public void appTicketUrl(String appTicketUrl) {
    this.appTicketUrl = appTicketUrl;
  }

}
