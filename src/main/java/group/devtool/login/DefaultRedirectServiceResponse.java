package group.devtool.login;

/**
 * 默认实现-应用重定向响应
 */
public class DefaultRedirectServiceResponse implements RedirectServiceResponse {

  private String location;

  /**
   * 应用重定向默认实现
   * 
   * @param redirectUrl 重定向地址
   */
  public DefaultRedirectServiceResponse(String redirectUrl) {
    this.location = redirectUrl;
  }

  @Override
  public String getLocation() {
    return location;
  }

}