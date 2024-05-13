package group.devtool.login;

/**
 * 抽象工厂类，根据登录协议请求，响应的类型返回对应的构造工厂
 */
public interface LoginProtocolFactory {

  /**
   * 返回请求构造工厂
   * 
   * @param clazz 请求类型
   * @return 请求构造工厂
   */
  LoginProtocolRequestFactory request(Class<? extends LoginProtocolRequest> clazz);

  /**
   * 返回响应构造工厂
   * 
   * @param clazz 响应类型
   * @return 响应构造工厂
   */
  LoginProtocolResponseFactory response(Class<? extends LoginProtocolResponse> clazz);

}
