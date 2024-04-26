package group.devtool.login.client;

import java.util.List;

public final class LoginClientConfiguration {

  private static LoginClientConfiguration config = new LoginClientConfiguration();

  private TokenRepository repository;

  private LoginClientProperties properties;

  private TokenSerializer serializer;

  private List<String> ignoreUrls;

  private ApplicationAuthorizeArgument argument;

  private HttpClient client;

  public LoginClientConfiguration() {

  }

  public static LoginClientConfiguration instance() {
    return config;
  }

  public static List<String> ignoreUrls() {
    return config.getIgnoreUrls();
  }

  public static void ignoreUrls(List<String> ignoreUrls) {
    config.setIgnoreUrls(ignoreUrls);
  }

  private List<String> getIgnoreUrls() {
    return ignoreUrls;
  }

  private void setIgnoreUrls(List<String> ignoreUrls) {
    this.ignoreUrls = ignoreUrls;
  }

  public static LoginClientProperties properties() {
    return config.getProperties();
  }

  public static void properties(LoginClientProperties properties) {
    config.setProperties(properties);
  }

  private LoginClientProperties getProperties() {
    return properties;
  }

  public void setProperties(LoginClientProperties properties) {
    this.properties = properties;
  }

  public static TokenSerializer serializer() {
    return config.getSerializer();
  }

  public static void serializer(TokenSerializer deserialize) {
    config.setSerializer(deserialize);
  }

  private TokenSerializer getSerializer() {
    return serializer;
  }

  public void setSerializer(TokenSerializer serialize) {
    this.serializer = serialize;
  }

  public static TokenRepository repository() {
    return config.getRepository();
  }

  public static void repository(TokenRepository registry) {
    config.setRepository(registry);
  }

  private TokenRepository getRepository() {
    return repository;
  }

  public void setRepository(TokenRepository registry) {
    this.repository = registry;
  }

  public static ApplicationAuthorizeArgument argument() {
    return config.getArgument();
  }

  public static void argument(ApplicationAuthorizeArgument construct) {
    config.setArgument(construct);
  }

  private ApplicationAuthorizeArgument getArgument() {
    return argument;
  }

  private void setArgument(ApplicationAuthorizeArgument construct) {
    this.argument = construct;
  }

  public static HttpClient client() {
    return config.getClient();
  }

  public static void client(HttpClient client) {
    config.setClient(client);
  }

  private HttpClient getClient() {
    return client;
  }

  public void setClient(HttpClient client) {
    this.client = client;
  }

}
