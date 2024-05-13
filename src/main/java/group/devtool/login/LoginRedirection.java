package group.devtool.login;

/**
 * 应用登录跳转地址构造器
 */
public interface LoginRedirection {

  /**
   * 根据当前请求，构造登录成功后的跳转地址
   * 
   * @param <Q>     请求类型参数
   * @param request 请求
   * @return 跳转地址
   */
  <Q> String loggedRedirectUrl(Q request);

  /**
   * 根据当前请求，构造登出后再次登入时跳转地址
   * 
   * @param <Q>     请求类型参数
   * @param request 当前请求
   * @return 登出后的跳转地址
   */
  <Q> String logoutRedirectUrl(Q request);
}
