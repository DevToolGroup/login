package group.devtool.login.server;

import group.devtool.login.client.LoginApplication;
import group.devtool.login.client.LoginToken;
import group.devtool.login.client.Ticket;

/**
 * 应用登录授权码
 */
public interface ApplicationTicket extends Ticket {

  /**
   * 登录申请的应用
   * 
   * @return
   */
  public LoginApplication application();

  /**
   * 颁发应用登录授权码的登录凭证
   * @return
   */
  public LoginTicket loginTicket();

  /**
   * 验证通过
   */
  default LoginToken confirm() {
    LoginToken token = doConfirm();
    loginTicket().loggedApplication().put(token.id(), application());
    return token;
  }

  public LoginToken doConfirm();

}
