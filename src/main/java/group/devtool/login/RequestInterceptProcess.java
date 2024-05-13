package group.devtool.login;

/**
 * 请求拦截器
 */
public interface RequestInterceptProcess {

  /**
   * 判断请求是否匹配
   * 
   * @param <Q> 实际请求类型
   * @param request 请求参数
   * @return true：匹配，false：不匹配
   */
  default <Q> boolean match(Q request) {
    return true;
  }

  /**
   * 请求处理器
   * 
   * @param <Q> 实际请求类型
   * @param <R> 实际响应类型
   * @param request 实际请求参数
   * @param response 实际响应
   * @throws Exception 可能的异常情况
   */
  public <Q, R> void process(Q request, R response) throws Exception;

}