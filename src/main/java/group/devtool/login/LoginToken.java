package group.devtool.login;

/**
 * 应用登录状态
 */
public interface LoginToken extends Trackable {

  /**
   * 登录状态的唯一标识，用来后续作为持久化的唯一标识
   * @return 登录状态唯一标识
   */
  public String id();

  /**
   * 是否过期
   * @return true：过期，false：未过期
   */
  public boolean isExpired();

  /**
   * 登录的实体信息，例如账号等
   * 
   * @return 登录的实体信息
   */
  public LoginAuthorization authorization();

  /**
   * 请求登录的应用信息
   * 
   * @return 应用信息
   */
  public LoginService application();

}
