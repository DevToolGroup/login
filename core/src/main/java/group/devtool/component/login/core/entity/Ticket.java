package group.devtool.component.login.core.entity;

/**
 * 登录凭证
 */
public interface Ticket extends Trackable {

  /**
   * @return 登录凭证唯一标识
   */
  String id();

  /**
   * 是否已过期
   * 
   * @return true：过期，false：未过期
   */
  boolean isExpired();
}
