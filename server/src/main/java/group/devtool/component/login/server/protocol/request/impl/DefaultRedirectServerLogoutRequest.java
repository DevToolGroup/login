package group.devtool.component.login.server.protocol.request.impl;


import group.devtool.component.login.core.service.LoginService;
import group.devtool.component.login.core.entity.DefaultLoginService;
import group.devtool.component.login.server.protocol.request.RedirectServerLogoutRequest;

/**
 * 登录服务退出请求默认实现类
 */
public class DefaultRedirectServerLogoutRequest implements RedirectServerLogoutRequest {

  private final String appId;

  private final String redirectUrl;

  /**
   * 初始化登录服务退出请求
   * 
   * @param appId       应用appId
   * @param redirectUrl 登录服务登出地址
   */
  public DefaultRedirectServerLogoutRequest(String appId, String redirectUrl) {
    this.appId = appId;
    this.redirectUrl = redirectUrl;
  }

  @Override
  public LoginService application() {
    return new DefaultLoginService(appId, redirectUrl);
  }

}
