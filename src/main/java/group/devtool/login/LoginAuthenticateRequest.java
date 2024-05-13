package group.devtool.login;

/**
 * 认证请求参数接口
 */
public interface LoginAuthenticateRequest extends LoginProtocolRequest {

  /**
   * @return 登录应用
   */
  LoginService loginApplication();

}
