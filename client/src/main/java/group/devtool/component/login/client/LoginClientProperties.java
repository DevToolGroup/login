package group.devtool.component.login.client;

/**
 * 客户端登录配置
 */
public class LoginClientProperties {

  private String appId;

  private String privateKey;

  private String authorizeUrl;

  private String serverLogoutUrl;

  private String clientLogoutUrl;

  private String serverValidateTicketUrl;

  private String applicationTicketUrl;

  /**
   * 获取客户端ID
   * 
   * @return 客户端ID
   */
  public String appId() {
    return appId;
  }

  /**
   * 设置客户端ID
   * 
   * @param appId 客户端ID
   */
  public void appId(String appId) {
    this.appId = appId;
  }

  /**
   * 获取客户端私钥
   * 
   * @return 客户端私钥
   */
  public String privateKey() {
    return privateKey;
  }

  /**
   * 设置客户端私钥
   * 
   * @param privateKey 客户端私钥
   */
  public void privateKey(String privateKey) {
    this.privateKey = privateKey;
  }

  /**
   * 获取授权登录地址，需要与服务端的授权登录地址匹配
   * 
   * @return 授权登录地址
   */
  public String serverAuthorizeUrl() {
    return authorizeUrl;
  }

  /**
   * 设置授权登录地址，需要与服务端的授权登录地址匹配
   * 
   * @param authorizeUrl 授权登录地址
   */
  public void serverAuthorizeUrl(String authorizeUrl) {
    this.authorizeUrl = authorizeUrl;
  }

  /**
   * 获取服务端登出调用地址，需要与服务端登记的地址匹配
   * 
   * @return 服务端登出调用地址
   */
  public String serverLogoutUrl() {
    return serverLogoutUrl;
  }

  /**
   * 设置服务端登出调用地址，需要与服务端登记的地址匹配
   * 
   * @param logoutUrl 服务端登出调用地址
   */
  public void serverLogoutUrl(String logoutUrl) {
    this.serverLogoutUrl = logoutUrl;
  }

  /**
   * 获取客户端登出地址
   * 
   * @return 客户端登出地址
   */
  public String clientLogoutUrl() {
    return clientLogoutUrl;
  }

  /**
   * 设置客户端登出地址
   * 
   * @param clientLogoutUrl 客户端登出地址
   */
  public void clientLogoutUrl(String clientLogoutUrl) {
    this.clientLogoutUrl = clientLogoutUrl;
  }

  /**
   * 获取应用登录授权码验证地址，需要与服务端的验证地址保持一致
   * 
   * @return 应用登录授权码验证地址
   */
  public String serverValidateTicketUrl() {
    return serverValidateTicketUrl;
  }

  /**
   * 获取应用登录授权码验证地址，需要与服务端的验证地址保持一致
   * 
   * @param validateUrl 应用登录授权码验证地址
   */
  public void serverValidateTicketUrl(String validateUrl) {
    this.serverValidateTicketUrl = validateUrl;
  }

  /**
   * 获取客户端登录凭证地址
   * 
   * @return 客户端登录凭证地址
   */
  public String clientApplicationTicketUrl() {
    return applicationTicketUrl;
  }

  /**
   * 设置客户端登录凭证地址
   * 
   * @param appTicketUrl 客户端登录凭证地址
   */
  public void clientApplicationTicketUrl(String appTicketUrl) {
    this.applicationTicketUrl = appTicketUrl;
  }

}
