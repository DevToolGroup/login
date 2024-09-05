package group.devtool.component.login.client.protocol.response;

import group.devtool.component.login.core.protocol.LoginRedirectResponse;

/**
 * 应用重定向响应
 */
public interface RedirectServiceResponse extends LoginRedirectResponse {

  @Override
  public String getLocation();

}
