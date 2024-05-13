package group.devtool.login;

/**
 * 登录服务退出请求接口
 */
public interface RedirectServerLogoutRequest extends LoginProtocolRequest{

  /**
   * @return 登录应用
   */
  LoginService application();

}
