package group.devtool.component.login.client.protocol.factory;

import group.devtool.component.login.client.protocol.request.RedirectApplicationTicketRequest;
import group.devtool.component.login.client.protocol.request.ServerLogoutRequest;
import group.devtool.component.login.client.protocol.request.ServerTicketValidateRequest;
import group.devtool.component.login.client.protocol.response.RedirectServerLogoutResponse;
import group.devtool.component.login.client.protocol.response.RedirectServiceAuthorizeResponse;
import group.devtool.component.login.client.protocol.response.RedirectServiceResponse;
import group.devtool.component.login.client.protocol.response.ServerLogoutResponse;
import group.devtool.component.login.core.protocol.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象工厂实现类，根据登录协议请求，响应的类型返回对应的构造工厂
 */
public class LoginClientProtocolFactory implements LoginProtocolFactory {

  private final Map<Class<? extends LoginProtocolRequest>, LoginProtocolRequestFactory> requestFactories;

  private final Map<Class<? extends LoginProtocolResponse>, LoginProtocolResponseFactory> responseFactories;

  /**
   * 客户端登录相关请求及响应的自定义抽象工厂实现类
   * 
   * @param requestFactories 自定义请求工厂实现类
   * @param responseFactories 自定义响应工厂实现类
   */
  public LoginClientProtocolFactory(Map<Class<? extends LoginProtocolRequest>, LoginProtocolRequestFactory> requestFactories,
      Map<Class<? extends LoginProtocolResponse>, LoginProtocolResponseFactory> responseFactories) {
    this.requestFactories = requestFactories;
    this.responseFactories = responseFactories;
  }

  /**
   * 客户端登录相关请求及响应的默认抽象工厂实现类
   */
  public LoginClientProtocolFactory() {
    requestFactories = initClientRequestFactories();
    responseFactories = initClientResponseFactories();
  }

  private Map<Class<? extends LoginProtocolResponse>, LoginProtocolResponseFactory> initClientResponseFactories() {
    Map<Class<? extends LoginProtocolResponse>, LoginProtocolResponseFactory> factories = new HashMap<>();
    factories.put(RedirectServiceResponse.class, new DefaultRedirectServiceResponseFactory());
    factories.put(RedirectServiceAuthorizeResponse.class, new DefaultRedirectServiceAuthorizeResponseFactory());
    factories.put(ServerLogoutResponse.class, new DefaultServerLogoutResponseFactory());
    factories.put(RedirectServerLogoutResponse.class, new DefaultRedirectServerLogoutResponseFactory());
    return factories;
  }

  private Map<Class<? extends LoginProtocolRequest>, LoginProtocolRequestFactory> initClientRequestFactories() {
    Map<Class<? extends LoginProtocolRequest>, LoginProtocolRequestFactory> factories = new HashMap<>();
    factories.put(RedirectApplicationTicketRequest.class, new DefaultRedirectServiceTicketRequestFactory());
    factories.put(ServerTicketValidateRequest.class, new DefaultServerTicketValidateRequestFactory());
    factories.put(ServerLogoutRequest.class, new DefaultServerLogoutRequestFactory());
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
