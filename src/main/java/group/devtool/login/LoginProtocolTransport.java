package group.devtool.login;

/**
 * 登录服务端与客户端信息交换处理器
 */
public interface LoginProtocolTransport {

  /**
   * 执行登录相关请求
   * 
   * @param <T> 返回结果类型必须为{@code LoginProtocolResponse}的子类
   * @param request 登录相关请求
   * @param clazz 返回结果具体类
   * @return 登录相关响应
   * @throws LoginException 登录请求异常
   */
  public <T extends LoginProtocolResponse> T execute(LoginProtocolRequest request, Class<T> clazz) throws LoginException;

}
