package group.devtool.login;

/**
 * 登录相关请求构造器
 */
public interface LoginProtocolRequestFactory {

  /**
   * 根据具体的参数构造登录请求
   * 
   * @param <T> 返回结果类型必须为 {@code LoginProtocolRequest} 的子类
   * @param args 实际场景的具体参数
   * @return 登录请求
   */
  <T extends LoginProtocolRequest> T create(Object... args);

}
