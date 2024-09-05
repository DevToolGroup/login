package group.devtool.component.login.core.service;


import group.devtool.component.login.core.protocol.LoginProtocolRequest;

/**
 * 登出服务接口
 */
public interface LogoutService {

  /**
   * 执行客户端登出请求
   * 
   * @param clientLogoutRequest 客户端登出请求
   */
  void logout(LoginProtocolRequest clientLogoutRequest);

}
