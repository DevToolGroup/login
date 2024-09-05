package group.devtool.component.login.core.protocol;

/**
 * 登录相关重定向响应
 */
public interface LoginRedirectResponse extends LoginProtocolResponse {

  /**
   * @return 重定向地址
   */
  public String getLocation();

}
