package group.devtool.component.login.core.service;

import group.devtool.component.login.core.TokenExpirePolicy;

/**
 * 注册的应用
 */
public interface RegisterService {

  /**
   * @return 应用AppID
   */
  public String id();

  /**
   * @return 应用公钥
   */
  public String publicKey();

  /**
   * @return 应用登出地址
   */
  public String logoutUrl();

  /**
   * @return 应用接收凭证地址
   */
  public String applicationTicketUrl();

  /**
   * @return 过期策略
   */
  public TokenExpirePolicy expirePolicy();

}
