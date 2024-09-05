package group.devtool.component.login.client.protocol.request;

import group.devtool.component.login.core.protocol.LoginProtocolRequest;

/**
 * 登录服务登出客户端请求
 */
public interface ServerLogoutRequest extends LoginProtocolRequest {

  /**
   * 客户端应用的登录标识
   * 
   * @return 登录标识
   */
  String getTokenId();

}
