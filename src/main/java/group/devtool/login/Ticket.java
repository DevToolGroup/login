package group.devtool.login;

/**
 * 登录凭证
 */
public interface Ticket extends Trackable {

  /**
   * @return 登录凭证唯一标识
   */
  public String id();

  /**
   * 是否已过期
   * 
   * @return true：过期，false：未过期
   */
  boolean isExpired();
}
