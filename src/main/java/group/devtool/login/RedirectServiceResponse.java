package group.devtool.login;

/**
 * 应用重定向响应
 */
public interface RedirectServiceResponse extends LoginRedirectResponse {

  @Override
  public String getLocation();

}
