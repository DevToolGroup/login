package group.devtool.login;

/**
 * 登录凭证限制器
 */
public interface LoginRestrictStrategy {

  /**
   * 校验登录凭证是否受限，如果受限抛出具体的异常，说明受限原因
   * 
   * @param ticket 登录凭证
   * @throws LoginRestrictException 登录授权异常
   */
  void restrict(LoginTicket ticket) throws LoginRestrictException;

}
