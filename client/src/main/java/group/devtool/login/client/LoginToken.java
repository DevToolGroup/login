package group.devtool.login.client;

/**
 * 应用登录状态标识
 */
public interface LoginToken extends TrackableToken {

  public String id();

  public boolean isExpired();

  public LoginAuthorization authorization();

  public LoginApplication application();

}
