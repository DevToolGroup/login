package group.devtool.component.login.server.protocol.request;

import group.devtool.component.login.core.protocol.LoginProtocolRequest;
import group.devtool.component.login.core.service.LoginService;

/**
 * 登录服务退出请求接口
 */
public interface RedirectServerLogoutRequest extends LoginProtocolRequest {

  /**
   * @return 登录应用
   */
  LoginService application();

}
