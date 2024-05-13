package group.devtool.login;


/**
 * 默认实现，重定向应用授权请求
 */
public class DefaultRedirectServiceAuthorizeRequest implements RedirectServiceAuthorizeRequest {

  private String appId;

  private String redirectUrl;

  /**
   * 初始化重定向应用授权请求
   * 
   * @param appId       应用appId
   * @param redirectUrl 重定向地址
   */
  public DefaultRedirectServiceAuthorizeRequest(String appId, String redirectUrl) {
    this.appId = appId;
    this.redirectUrl = redirectUrl;

  }

  @Override
  public LoginService service() {
    return new DefaultLoginService(appId, redirectUrl);
  }

}
