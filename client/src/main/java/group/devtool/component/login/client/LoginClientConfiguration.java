package group.devtool.component.login.client;

import group.devtool.component.login.core.LoginProtocolTransport;
import group.devtool.component.login.core.entity.LoginRedirection;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.core.repository.LoginTokenRepository;

import java.util.List;

/**
 * 客户端登录拦截器依赖配置
 */
public final class LoginClientConfiguration {

  /**
   * 依赖配置单例
   */
  private static final LoginClientConfiguration config = new LoginClientConfiguration();

  private List<String> ignoreUrls;

  private LoginTokenRepository manager;

  private LoginClientProperties properties;

  private LoginRedirection redirection;

  private LoginProtocolTransport transport;

  private LoginProtocolFactory protocol;

  private LoginClientConfiguration() {

  }

  /**
   * 获取客户端登录拦截器依赖配置实例
   * 
   * @return 客户端登录拦截器依赖配置实例
   */
  public static LoginClientConfiguration instance() {
    return config;
  }

  /**
   * 获取忽略登录状态的请求地址集合
   * 
   * @return ignoreUrls 客户端忽略登录状态的请求地址集合
   */
  public static List<String> ignoreUrls() {
    return config.getIgnoreUrls();
  }

  /**
   * 设置忽略登录状态的请求地址集合
   * 
   * @param ignoreUrls 客户端忽略登录状态的请求地址集合
   */
  public static void ignoreUrls(List<String> ignoreUrls) {
    config.setIgnoreUrls(ignoreUrls);
  }

  private List<String> getIgnoreUrls() {
    return ignoreUrls;
  }

  private void setIgnoreUrls(List<String> ignoreUrls) {
    this.ignoreUrls = ignoreUrls;
  }

  /**
   * 获取客户端登录相关配置
   * 
   * @return 客户端登录配置
   */
  public static LoginClientProperties properties() {
    return config.getProperties();
  }

  /**
   * 设置客户端登录相关配置
   * 
   * @param properties 客户端登录配置
   */
  public static void properties(LoginClientProperties properties) {
    config.setProperties(properties);
  }

  private LoginClientProperties getProperties() {
    return properties;
  }

  private void setProperties(LoginClientProperties properties) {
    this.properties = properties;
  }

  /**
   * 获取客户端登录状态管理器
   * 
   * @return 客户端登录状态管理器
   */
  public static LoginTokenRepository manager() {
    return config.getManager();
  }

  /**
   * 设置客户端登录状态管理器
   * 
   * @param manager 客户端登录状态管理器
   */
  public static void manager(LoginTokenRepository manager) {
    config.setManager(manager);
  }

  private LoginTokenRepository getManager() {
    return manager;
  }

  private void setManager(LoginTokenRepository manager) {
    this.manager = manager;
  }

  /**
   * 获取客户端跳转链接构造器
   * 
   * @return 跳转链接构造器
   */
  public static LoginRedirection redirection() {
    return config.getRedirection();
  }

  /**
   * 设置客户端跳转链接构造器
   * 
   * @param redirection 跳转链接构造器
   */
  public static void redirection(LoginRedirection redirection) {
    config.setRedirection(redirection);
  }

  private LoginRedirection getRedirection() {
    return redirection;
  }

  private void setRedirection(LoginRedirection redirection) {
    this.redirection = redirection;
  }

  /**
   * 获取登录服务端与客户端信息交换处理器
   * 
   * @return 登录服务端与客户端信息交换处理器
   */
  public static LoginProtocolTransport transport() {
    return config.getTransport();
  }

  /**
   * 设置登录服务端与客户端信息交换处理器
   * 
   * @param client 登录服务端与客户端信息交换处理器
   */
  public static void transport(LoginProtocolTransport client) {
    config.setTransport(client);
  }

  private LoginProtocolTransport getTransport() {
    return transport;
  }

  private void setTransport(LoginProtocolTransport transport) {
    this.transport = transport;
  }

  /**
   * 获取登录相关请求对应的抽象构造工厂
   * 
   * @return 抽象构造工厂
   */
  public static LoginProtocolFactory protocol() {
    return config.getProtocol();
  }

  /**
   * 设置登录相关请求对应的抽象构造工厂
   * 
   * @param protocol 抽象构造工厂
   */
  public static void protocol(LoginProtocolFactory protocol) {
    config.setProtocol(protocol);
  }

  private LoginProtocolFactory getProtocol() {
    return protocol;
  }

  private void setProtocol(LoginProtocolFactory protocol) {
    this.protocol = protocol;
  }

}
