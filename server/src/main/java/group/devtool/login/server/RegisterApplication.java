package group.devtool.login.server;

import group.devtool.login.client.TokenExpirePolicy;

/**
 * 注册的应用
 */
public interface RegisterApplication {

  public String id();

  public String publicKey();

  public String logoutUrl();

  public String validateTicketUrl();

  public TokenExpirePolicy expirePolicy();

}
