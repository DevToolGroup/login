package group.devtool.login;

/**
 * 登录应用信息默认实现
 */
public class DefaultLoginService implements LoginService {

  private static final long serialVersionUID = 9153637541901406503L;

  /**
   * 应用appId
   */
  private String appId;

  /**
   * 登录后重定向地址
   */
  private String redirectUrl;

  /**
   * 根据应用appId及登录后重定向地址，构造登录应用
   * 
   * @param appId 应用appId
   * @param redirectUrl 登录后重定向地址
   */
  public DefaultLoginService(String appId, String redirectUrl) {
    this.appId = appId;
    this.redirectUrl = redirectUrl;
  }

  @Override
  public String appId() {
    return appId;
  }

  @Override
  public String redirectUrl() {
    return redirectUrl;
  }

}
