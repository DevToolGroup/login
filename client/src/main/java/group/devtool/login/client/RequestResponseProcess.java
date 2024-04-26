package group.devtool.login.client;

/**
 * 请求响应处理器
 */
public interface RequestResponseProcess {

  /**
   * 解析请求链接地址，不包含域名，请求参数
   * 
   * @param request
   * @return
   */
  <Q> String uri(Q request);

  /**
   * 解析请求链接地址
   * 
   * @param request
   * @return
   */
  <Q> String url(Q request);

  /**
   * 登出，再登入跳转URL
   * 
   * @param request 登出请求
   * @return
   */
  <Q> String logoutRedirectUrl(Q request);

  /**
   * 根据name解析cookie，并返回结果
   * 
   * @param name Cookie参数名称
   * @return cookie参数的结果
   */
  <Q> String cookie(Q request, String name);

  /**
   * 设置登录上下文
   * 
   * @param authorization 登录实体信息
   */
  void saveLogin(LoginAuthorization authorization);

  /**
   * 清除登录上下文
   * 
   * @param authorization
   */
  void removeLogin(LoginAuthorization authorization);

  /**
   * 构造异常响应
   * 
   * @param response Http响应
   * @param e        异常信息
   */
  <R> void error(R response, LoginException e);

  /**
   * 构造重定向响应
   * 
   * @param response    Http响应
   * @param redirectUrl 重定向URL
   * @param cookies     登录相关cookie
   */
  <R> void redirect(R response, String redirectUrl, LoginCookie... cookies);

  /**
   * 构造成功响应
   * 
   * @param response Http响应
   * @param entity   登录实体信息
   * @param cookies  登录相关cookie
   */
  <T, R> void success(R response, T entity, LoginCookie... cookies);

}
