package group.devtool.component.login.core.entity;


import group.devtool.component.login.core.service.LoginService;

/**
 * 应用登录授权码
 */
public interface ServiceTicket extends Ticket {

  /**
   * @return 登录应用
   */
  public LoginService service();

  /**
   * 颁发应用的登录凭证
   * @return 应用登录凭证
   */
  public LoginTicket loginTicket();

  /**
   * 确认应用凭证，返回登录状态
   * 
   * @return 返回登录状态
   */
  public LoginToken confirm();


}
