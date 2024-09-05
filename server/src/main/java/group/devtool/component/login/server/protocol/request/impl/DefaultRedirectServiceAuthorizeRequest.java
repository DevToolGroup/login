package group.devtool.component.login.server.protocol.request.impl;


import group.devtool.component.login.core.service.LoginService;
import group.devtool.component.login.core.entity.DefaultLoginService;
import group.devtool.component.login.server.protocol.request.RedirectServiceAuthorizeRequest;

/**
 * 默认实现，重定向应用授权请求
 */
public class DefaultRedirectServiceAuthorizeRequest implements RedirectServiceAuthorizeRequest {

  private final String appId;

  private final String redirectUrl;

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
