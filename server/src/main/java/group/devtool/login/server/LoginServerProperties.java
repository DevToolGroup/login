package group.devtool.login.server;

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

  private String loginContextPath;

  public LoginServerProperties() {

  }

  public String authenticateUrl() {
    return authenticateUrl;
  }

  public void authenticateUrl(String authenticateUrl) {
    this.authenticateUrl = authenticateUrl;
  }

  public String authorizeUrl() {
    return authorizeUrl;
  }

  public void authorizeUrl(String authorizeUrl) {
    this.authorizeUrl = authorizeUrl;
  }

  public String validateUrl() {
    return validateUrl;
  }

  public void validateUrl(String validateUrl) {
    this.validateUrl = validateUrl;
  }

  public String logoutUrl() {
    return logoutUrl;
  }

  public void logoutUrl(String logoutUrl) {
    this.logoutUrl = logoutUrl;
  }

  public String appId() {
    return appId;
  }

  public void appId(String appId) {
    this.appId = appId;
  }

  public String loginContextPath() {
    return loginContextPath;
  }

  public void loginContextPath(String loginContextPath) {
    this.loginContextPath = loginContextPath;
  }
}