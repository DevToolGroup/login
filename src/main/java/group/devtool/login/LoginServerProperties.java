package group.devtool.login;

/**
 * 登录服务端配置
 */
public class LoginServerProperties {

  /**
   * 应用登录授权地址
   */
  private String authorizeUrl;

  /**
   * 登录认证链接
   */
  private String authenticateUrl;

  /**
   * 客户端登录凭证验证链接
   */
  private String validateUrl;

  /**
   * 登出地址
   */
  private String logoutUrl;

  /**
   * 服务端APPID
   */
  private String appId;

  private String loginContext;

  /**
   * 初始化登录服务配置
   */
  public LoginServerProperties() {

  }

  /**
   * @return 认证地址
   */
  public String authenticateUrl() {
    return authenticateUrl;
  }

  /**
   * 设置认证地址
   * 
   * @param authenticateUrl 认证地址
   */
  public void authenticateUrl(String authenticateUrl) {
    this.authenticateUrl = authenticateUrl;
  }

  /**
   * @return 应用登录授权地址
   */
  public String authorizeUrl() {
    return authorizeUrl;
  }

  /**
   * 设置应用登录授权地址
   * 
   * @param authorizeUrl 应用登录授权地址
   */
  public void authorizeUrl(String authorizeUrl) {
    this.authorizeUrl = authorizeUrl;
  }

  /**
   * @return 应用登录凭证校验地址
   */
  public String validateUrl() {
    return validateUrl;
  }

  /**
   * 设置应用登录凭证校验地址
   * 
   * @param validateUrl 应用登录凭证校验地址
   */
  public void validateUrl(String validateUrl) {
    this.validateUrl = validateUrl;
  }

  /**
   * @return 登录服务登出地址
   */
  public String logoutUrl() {
    return logoutUrl;
  }

  /**
   * 设置登录服务登出地址
   * 
   * @param logoutUrl 登录服务登出地址
   */
  public void logoutUrl(String logoutUrl) {
    this.logoutUrl = logoutUrl;
  }

  /**
   * @return 应用APPID
   */
  public String appId() {
    return appId;
  }

  /**
   * 设置应用APPID
   * 
   * @param appId 应用APPID
   */
  public void appId(String appId) {
    this.appId = appId;
  }

  /**
   * @return 登录相关请求上下文
   */
  public String loginContext() {
    return loginContext;
  }

  /**
   * 设置登录相关请求上下文
   * 
   * @param loginContext 登录相关请求上下文
   */
  public void loginContext(String loginContext) {
    this.loginContext = loginContext;
  }
}