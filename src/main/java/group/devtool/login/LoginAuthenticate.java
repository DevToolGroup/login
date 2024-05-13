package group.devtool.login;


/**
 * 登录认证服务
 */
public interface LoginAuthenticate {

  /**
   * 构造认证服务登录地址，一般情况下为认证服务的登录页面地址
   * 
   * @param redirectResponse 认证服务登录成功，跳转登录服务的认证地址
   * @return 登录URL
   */
  LoginRedirectResponse loginUrl(LoginRedirectResponse redirectResponse);

  /**
   * 登录认证
   * 
   * @param <Q>     请求类型
   * @param request 认证请求
   * @return 认证信息
   * @throws LoginAuthenticateException 认证异常
   */
  default <Q> LoginAuthorizationResult authenticate(Q request) throws LoginAuthenticateException {
    LoginAuthorizationResult result = doAuthenticate(request);
    doAuthenticateComplete(request, result);
    return result;
  }

  /**
   * 执行登录认证操作
   * 
   * @param <Q>     请求参数类型
   * @param request 请求
   * @return 认证结果
   * @throws LoginAuthenticateException 认证异常
   */
  <Q> LoginAuthorizationResult doAuthenticate(Q request) throws LoginAuthenticateException;

  /**
   * 认证完成后续操作
   * 
   * @param <Q>     请求类型
   * @param request 请求
   * @param result  认证结果
   * @throws LoginAuthenticateException 认证异常
   */
  default <Q> void doAuthenticateComplete(Q request, LoginAuthorizationResult result)
      throws LoginAuthenticateException {

  }

}
