package group.devtool.login;

import java.util.Map;

/**
 * 请求响应处理器
 */
public interface RequestResponseProcess {

  /**
   * 解析请求链接路径
   * 
   * @param <Q>     请求实际类型
   * @param request 请求
   * @return 链接路径
   */
  <Q> String uri(Q request);

  /**
   * 解析请求链接地址
   * 
   * @param <Q>     请求实际类型
   * @param request 请求
   * @return 链接地址
   */
  <Q> String url(Q request);

  /**
   * 解析请求参数
   * 
   * @param <Q>     请求实际类型
   * @param request 请求参数
   * @return 请求参数集合
   */
  <Q> Map<String, String> query(Q request);

  /**
   * 根据name解析cookie，并返回结果
   * 
   * @param <Q>     请求实际类型
   * @param request 请求
   * @param name    Cookie参数名称
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
   * @param authorization 认证结果
   */
  void removeLogin(LoginAuthorization authorization);

  /**
   * 构造异常响应
   * 
   * @param <R>      响应实际类型
   * @param response Http响应
   * @param e        异常信息
   */
  <R> void error(R response, LoginException e);

  /**
   * 构造重定向响应
   * 
   * @param <R>              响应实际类型
   * @param response         Http响应
   * @param redirectResponse 重定向URL
   * @param cookies          登录相关cookie
   */
  <R> void redirect(R response, LoginRedirectResponse redirectResponse, LoginCookie... cookies);

  /**
   * 构造成功响应
   * 
   * @param <R>              响应实际类型
   * @param response         Http响应
   * @param protocolResponse 登录响应协议
   * @param cookies          登录相关cookie
   */
  <R> void success(R response, LoginProtocolResponse protocolResponse, LoginCookie... cookies);

}
