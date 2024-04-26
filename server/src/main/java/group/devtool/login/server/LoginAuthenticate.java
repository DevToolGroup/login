package group.devtool.login.server;

/**
 * 登录认证服务
 */
public interface LoginAuthenticate {

  /**
   * 构造登录地址。
   * 
   * @param authenticateUrl 登录成功后的跳转地址
   * @return 登录URL
   */
  String loginUrl(String authenticateUrl);

  /**
   * 登录认证
   * 
   * @param request 认证请求
   * @return 认证信息
   * @throws LoginAuthenticateException 认证异常
   */
  default <Q> LoginAuthorizationResult authenticate(Q request) throws LoginAuthenticateException {
    LoginAuthorizationResult result = doAuthenticate(request);
    doAuthenticateComplete(request, result);
    return result;
  }

  <Q> LoginAuthorizationResult doAuthenticate(Q request) throws LoginAuthenticateException;

  default <Q> void doAuthenticateComplete(Q request, LoginAuthorizationResult result)
      throws LoginAuthenticateException {

  }

}
