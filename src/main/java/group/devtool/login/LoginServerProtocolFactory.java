package group.devtool.login;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象工厂类，根据登录协议请求，响应的类型返回对应的构造工厂
 */
public class LoginServerProtocolFactory implements LoginProtocolFactory {

  private final Map<Class<? extends LoginProtocolRequest>, LoginProtocolRequestFactory> requestFactories;

  private final Map<Class<? extends LoginProtocolResponse>, LoginProtocolResponseFactory> responseFactories;

  /**
   * 初始化自定义构造工厂
   * 
   * @param requestFactories  请求相关构造工厂
   * @param responseFactories 响应相关构造工厂
   */
  public LoginServerProtocolFactory(
      Map<Class<? extends LoginProtocolRequest>, LoginProtocolRequestFactory> requestFactories,
      Map<Class<? extends LoginProtocolResponse>, LoginProtocolResponseFactory> responseFactories) {
    this.requestFactories = requestFactories;
    this.responseFactories = responseFactories;
  }

  /**
   * 初始化默认构造工厂
   */
  public LoginServerProtocolFactory() {
    requestFactories = initClientRequestFactories();
    responseFactories = initClientResponseFactories();
  }

  private Map<Class<? extends LoginProtocolResponse>, LoginProtocolResponseFactory> initClientResponseFactories() {
    Map<Class<? extends LoginProtocolResponse>, LoginProtocolResponseFactory> factories = new HashMap<>();
    factories.put(RedirectServiceTicketResponse.class, new DefaultRedirectServiceTicketResponseFactory());
    factories.put(RedirectAuthenticateResponse.class, new DefaultRedirectAuthenticateResponseFactory());
    factories.put(TicketValidateResponse.class, new DefaultTicketValidateResponseFactory());
    factories.put(RedirectAuthorizeResponse.class, new DefaultRedirectAuthorizeResponseFactory());
    return factories;
  }

  private Map<Class<? extends LoginProtocolRequest>, LoginProtocolRequestFactory> initClientRequestFactories() {
    Map<Class<? extends LoginProtocolRequest>, LoginProtocolRequestFactory> factories = new HashMap<>();
    factories.put(RedirectServiceAuthorizeRequest.class, new DefaultRedirectServiceAuthorizeRequestFactory());
    factories.put(ClientTicketValidateRequest.class, new DefaultClientTicketValidateRequestFactory());
    factories.put(RedirectServerLogoutRequest.class, new DefaultRedirectServerLogoutRequestFactory());
    factories.put(ClientLogoutRequest.class, new DefaultClientLogoutRequestFactory());
    factories.put(RedirectServerLogoutRequest.class, new DefaultRedirectServerLogoutRequestFactory());
    return factories;
  }

  /**
   * 返回请求构造工厂
   * 
   * @param clazz 请求类型
   * @return 请求构造工厂
   */
  public LoginProtocolRequestFactory request(Class<? extends LoginProtocolRequest> clazz) {
    return requestFactories.get(clazz);
  }

  /**
   * 返回响应构造工厂
   * 
   * @param clazz 响应类型
   * @return 响应构造工厂
   */
  public LoginProtocolResponseFactory response(Class<? extends LoginProtocolResponse> clazz) {
    return responseFactories.get(clazz);
  }

}
