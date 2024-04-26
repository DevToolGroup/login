package group.devtool.login.server;

import java.util.List;

import group.devtool.login.client.ApplicationAuthorizeArgument;
import group.devtool.login.client.TokenExpirePolicy;
import group.devtool.login.client.TokenSerializer;

/**
 * 登录服务端配置
 */
public class LoginServerConfiguration {

  private static LoginServerConfiguration config = new LoginServerConfiguration();

  private List<String> ignoreUrls;

  private LoginServerProperties properties;

  private LoginAuthenticate authenticate;

  private LoginRestrictStrategy restrict;

  private IdGenerator idGenerator;

  private LoginTicketFactory factory;

  private ApplicationRepository applications;

  private TicketRepository repository;

  private TokenExpirePolicy expirePolicy;

  private TokenSerializer serializer;

  private LogoutService logoutService;

  private ApplicationAuthorizeArgument argument;

  public LoginServerConfiguration() {

  }

  public static LoginServerConfiguration instance() {
    return config;
  }

  public static LoginServerProperties properties() {
    return config.getProperties();
  }

  public static void properties(LoginServerProperties properties) {
    config.setProperties(properties);
  }

  private void setProperties(LoginServerProperties properties) {
    this.properties = properties;
  }

  private LoginServerProperties getProperties() {
    return properties;
  }

  public static LoginAuthenticate authenticate() {
    return config.getAuthenticate();
  }

  public static void authenticate(LoginAuthenticate authenticate) {
    config.setAuthenticate(authenticate);
  }

  private void setAuthenticate(LoginAuthenticate authenticate) {
    this.authenticate = authenticate;
  }

  private LoginAuthenticate getAuthenticate() {
    return authenticate;
  }

  public static LoginRestrictStrategy restrict() {
    return config.getRestrict();
  }

  public static void restrict(LoginRestrictStrategy restrict) {
    config.setRestrict(restrict);
  }

  private LoginRestrictStrategy getRestrict() {
    return restrict;
  }

  private void setRestrict(LoginRestrictStrategy restrict) {
    this.restrict = restrict;
  }

  public static LoginTicketFactory factory() {
    return config.getFactory();
  }

  public static void factory(LoginTicketFactory factory) {
    config.setFactory(factory);
  }

  private LoginTicketFactory getFactory() {
    return factory;
  }

  private void setFactory(LoginTicketFactory ticketFactory) {
    this.factory = ticketFactory;
  }

  public static ApplicationRepository applications() {
    return config.getApplications();
  }

  public static void applications(ApplicationRepository repository) {
    config.setApplications(repository);
  }

  private ApplicationRepository getApplications() {
    return applications;
  }

  private void setApplications(ApplicationRepository repository) {
    this.applications = repository;
  }

  public static LogoutService logoutService() {
    return config.getLogoutService();
  }

  public static void logoutService(LogoutService logoutService) {
    config.setLogoutService(logoutService);
  }

  private LogoutService getLogoutService() {
    return logoutService;
  }

  public void setLogoutService(LogoutService logoutService) {
    this.logoutService = logoutService;
  }

  public static TicketRepository repository() {
    return config.getRepository();
  }

  public static void repository(TicketRepository registry) {
    config.setRepository(registry);
  }

  private TicketRepository getRepository() {
    return repository;
  }

  public void setRepository(TicketRepository registry) {
    this.repository = registry;
  }

  public static List<String> ignoreUrls() {
    return config.getIgnoreUrls();
  }

  public static void ignoreUrls(List<String> ignoreUrls) {
    config.setIgnoreUrls(ignoreUrls);
  }

  private void setIgnoreUrls(List<String> ignoreUrls) {
    this.ignoreUrls = ignoreUrls;
  }

  private List<String> getIgnoreUrls() {
    return ignoreUrls;
  }

  public static IdGenerator idGenerator() {
    return config.getIdGenerator();
  }

  public static void idGenerator(IdGenerator idGenerator) {
    config.setIdGenerator(idGenerator);
  }

  private IdGenerator getIdGenerator() {
    return idGenerator;
  }

  private void setIdGenerator(IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  public static TokenExpirePolicy expire() {
    return config.getExpirePolicy();
  }

  public static void expire(TokenExpirePolicy policy) {
    config.setExpirePolicy(policy);
  }

  private TokenExpirePolicy getExpirePolicy() {
    return expirePolicy;
  }

  private void setExpirePolicy(TokenExpirePolicy policy) {
    this.expirePolicy = policy;
  }

  public static TokenSerializer serializer() {
    return config.getSerializer();
  }

  public static void serializer(TokenSerializer serialize) {
    config.setSerializer(serialize);
  }

  private TokenSerializer getSerializer() {
    return serializer;
  }

  private void setSerializer(TokenSerializer serialize) {
    this.serializer = serialize;
  }

  public static ApplicationAuthorizeArgument argument() {
    return config.getDynamicApplicationArgument();
  }

  public static void argument(ApplicationAuthorizeArgument construct) {
    config.setDynamicApplicationArgument(construct);
  }

  private ApplicationAuthorizeArgument getDynamicApplicationArgument() {
    return argument;
  }

  public void setDynamicApplicationArgument(ApplicationAuthorizeArgument construct) {
    this.argument = construct;
  }

}
