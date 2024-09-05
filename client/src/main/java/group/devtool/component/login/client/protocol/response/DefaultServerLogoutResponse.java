package group.devtool.component.login.client.protocol.response;

/**
 * 默认实现，根据客户端登出结果，构造登录服务登出客户端响应
 */
public class DefaultServerLogoutResponse implements ServerLogoutResponse {

  private final boolean result;

  /**
   * 根据客户端登出结果，构造登录服务登出客户端响应 
   * 
   * @param result 登出结果，true：登出成功，false：登出失败
   */
  public DefaultServerLogoutResponse(boolean result) {
    this.result = result;
  }

  /**
   * 登出结果
   * 
   * @return true：登出成功，false：登出失败
   */
  public boolean getResult() {
    return result;
  }

}
