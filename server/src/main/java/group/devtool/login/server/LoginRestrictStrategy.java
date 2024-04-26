package group.devtool.login.server;

/**
 * 登录凭证限制器
 */
public interface LoginRestrictStrategy {

  void restrict(LoginTicket ticket) throws LoginRestrictException;

}
