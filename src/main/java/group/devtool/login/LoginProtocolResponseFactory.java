package group.devtool.login;

/**
 * 登录响应构造工厂
 */
public interface LoginProtocolResponseFactory {

  /**
   * 根据参数构造登录响应
   * @param <T> 登录请求响应类型
   * @param args 参数列表
   * @return 登录请求响应
   */
  public <T extends LoginProtocolResponse> T create(Object... args);
}
