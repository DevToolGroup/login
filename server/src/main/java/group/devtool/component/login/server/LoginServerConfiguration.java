package group.devtool.component.login.server;


import group.devtool.component.login.core.LoginRestrictStrategy;
import group.devtool.component.login.core.TokenExpirePolicy;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.core.LoginTicketFactory;
import group.devtool.component.login.core.repository.LoginTicketRepository;
import group.devtool.component.login.core.repository.ServiceRepository;
import group.devtool.component.login.core.service.LoginAuthenticate;
import group.devtool.component.login.core.service.LogoutService;
import group.devtool.component.login.core.service.TicketIdGenerator;

/**
 * 登录服务端配置
 */
public class LoginServerConfiguration {

  private static final LoginServerConfiguration config = new LoginServerConfiguration();

  private LoginServerProperties properties;

  private LoginAuthenticate authenticate;

  private LoginRestrictStrategy restrict;

  private TicketIdGenerator idGenerator;

  private LoginTicketFactory factory;

  private ServiceRepository applications;

  private LoginTicketRepository manager;

  private TokenExpirePolicy expirePolicy;

  private LogoutService logoutService;

  private LoginProtocolFactory protocol;

  private LoginServerConfiguration() {

  }

  /**
   * @return 登录服务实例
   */
  public static LoginServerConfiguration instance() {
    return config;
  }

  /**
   * @return 登录服务配置
   */
  public static LoginServerProperties properties() {
    return config.getProperties();
  }

  /**
   * 设置登录服务配置
   * 
   * @param properties 登录服务配置
   */
  public static void properties(LoginServerProperties properties) {
    config.setProperties(properties);
  }

  private void setProperties(LoginServerProperties properties) {
    this.properties = properties;
  }

  private LoginServerProperties getProperties() {
    return properties;
  }

  /**
   * @return 认证服务
   */
  public static LoginAuthenticate authenticate() {
    return config.getAuthenticate();
  }

  /**
   * 设置认证服务
   * 
   * @param authenticate 认证服务
   */
  public static void authenticate(LoginAuthenticate authenticate) {
    config.setAuthenticate(authenticate);
  }

  private void setAuthenticate(LoginAuthenticate authenticate) {
    this.authenticate = authenticate;
  }

  private LoginAuthenticate getAuthenticate() {
    return authenticate;
  }

  /**
   * 获取登录凭证限制策略
   * 
   * @return 限制策略
   */
  public static LoginRestrictStrategy restrict() {
    return config.getRestrict();
  }

  /**
   * 设置登录凭证限制策略
   * 
   * @param restrict 登录凭证限制策略
   */
  public static void restrict(LoginRestrictStrategy restrict) {
    config.setRestrict(restrict);
  }

  private LoginRestrictStrategy getRestrict() {
    return restrict;
  }

  private void setRestrict(LoginRestrictStrategy restrict) {
    this.restrict = restrict;
  }

  /**
   * @return 登录凭证构造工厂
   */
  public static LoginTicketFactory factory() {
    return config.getFactory();
  }

  /**
   * 设置登录凭证构造工厂
   * 
   * @param factory 登录凭证构造工厂
   */
  public static void factory(LoginTicketFactory factory) {
    config.setFactory(factory);
  }

  private LoginTicketFactory getFactory() {
    return factory;
  }

  private void setFactory(LoginTicketFactory ticketFactory) {
    this.factory = ticketFactory;
  }

  /**
   * 获取应用持久化服务
   * 
   * @return 应用持久化服务
   */
  public static ServiceRepository applications() {
    return config.getApplications();
  }

  /**
   * 设置应用持久化服务
   * 
   * @param repository 应用持久化服务
   */
  public static void applications(ServiceRepository repository) {
    config.setApplications(repository);
  }

  private ServiceRepository getApplications() {
    return applications;
  }

  private void setApplications(ServiceRepository repository) {
    this.applications = repository;
  }

  /**
   * @return 登出请求处理器
   */
  public static LogoutService logoutService() {
    return config.getLogoutService();
  }

  /**
   * 设置登出请求处理器
   * 
   * @param logoutService 登出请求处理器
   */
  public static void logoutService(LogoutService logoutService) {
    config.setLogoutService(logoutService);
  }

  private LogoutService getLogoutService() {
    return logoutService;
  }

  private void setLogoutService(LogoutService logoutService) {
    this.logoutService = logoutService;
  }

  /**
   * @return 登录凭证管理器
   */
  public static LoginTicketRepository manager() {
    return config.getManager();
  }

  /**
   * 设置登录凭证管理器
   * 
   * @param manager 登录凭证管理器
   */
  public static void manager(LoginTicketRepository manager) {
    config.setManager(manager);
  }

  private LoginTicketRepository getManager() {
    return manager;
  }

  private void setManager(LoginTicketRepository manager) {
    this.manager = manager;
  }

  /**
   * @return 登录凭证标识生成器
   */
  public static TicketIdGenerator idGenerator() {
    return config.getIdGenerator();
  }

  /**
   * 设置登录凭证标识生成器
   * 
   * @param idGenerator 登录凭证标识生成器
   */
  public static void idGenerator(TicketIdGenerator idGenerator) {
    config.setIdGenerator(idGenerator);
  }

  private TicketIdGenerator getIdGenerator() {
    return idGenerator;
  }

  private void setIdGenerator(TicketIdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  /**
   * @return 登录状态过期策略
   */
  public static TokenExpirePolicy expire() {
    return config.getExpirePolicy();
  }

  /**
   * 登录状态过期策略
   * 
   * @param policy 登录状态过期策略
   */
  public static void expire(TokenExpirePolicy policy) {
    config.setExpirePolicy(policy);
  }

  private TokenExpirePolicy getExpirePolicy() {
    return expirePolicy;
  }

  private void setExpirePolicy(TokenExpirePolicy policy) {
    this.expirePolicy = policy;
  }

  /**
   * @return 登录协议抽象工厂
   */
  public static LoginProtocolFactory protocol() {
    return config.getProtocol();
  }

  /**
   * 设置登录协议抽象工厂
   * 
   * @param protocol 登录协议抽象工厂
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
