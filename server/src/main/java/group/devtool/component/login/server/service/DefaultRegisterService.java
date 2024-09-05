package group.devtool.component.login.server.service;

import group.devtool.component.login.core.TokenExpirePolicy;
import group.devtool.component.login.core.policy.HardTimeTicketExpirePolicy;
import group.devtool.component.login.core.service.RegisterService;

import java.util.UUID;

/**
 * 默认时间，注册应用
 */
public class DefaultRegisterService implements RegisterService {

  private String id;

  private final String publicKey;

  private final String logoutUrl;

  private final String ticketUrl;

  private final Long ticketTimeToLive;

  /**
   * 初始化注册应用
   * 
   * @param id               应用AppID
   * @param publicKey        应用公钥
   * @param logoutUrl        登出地址
   * @param ticketUrl        登录凭证接收地址
   * @param ticketTimeToLive 凭证有效期
   */
  public DefaultRegisterService(String id, String publicKey, String logoutUrl, String ticketUrl,
      Long ticketTimeToLive) {
    this.id = id;
    this.publicKey = publicKey;
    this.logoutUrl = logoutUrl;
    this.ticketUrl = ticketUrl;
    this.ticketTimeToLive = ticketTimeToLive;
  }

  @Override
  public String id() {
    if (null == id) {
      id = UUID.randomUUID().toString();
    }
    return id;
  }

  @Override
  public String publicKey() {
    return publicKey;
  }

  @Override
  public String logoutUrl() {
    return logoutUrl;
  }

  @Override
  public TokenExpirePolicy expirePolicy() {
    return new HardTimeTicketExpirePolicy(ticketTimeToLive);
  }

  @Override
  public String applicationTicketUrl() {
    return ticketUrl;
  }
}
