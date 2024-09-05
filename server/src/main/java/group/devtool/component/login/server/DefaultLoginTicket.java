package group.devtool.component.login.server;

import group.devtool.component.login.core.policy.HardTimeTicketExpirePolicy;
import group.devtool.component.login.core.TokenExpirePolicy;
import group.devtool.component.login.core.entity.LoginAuthorization;
import group.devtool.component.login.core.entity.LoginTicket;
import group.devtool.component.login.core.entity.ServiceTicket;
import group.devtool.component.login.core.entity.TimeTrackable;
import group.devtool.component.login.core.service.LoginService;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录凭证默认实现
 */
public class DefaultLoginTicket extends TimeTrackable implements LoginTicket {

  private static final long serialVersionUID = -5835606221720401882L;

  /**
   * 凭证标识
   */
  private final String id;

  /**
   * 认证结果
   */
  private final LoginAuthorization authorization;

  /**
   * 过期策略
   */
  private final TokenExpirePolicy expirePolicy;

  /**
   * 创建时间
   */
  private final Long createTime;

  /**
   * 已授权应用
   */
  private Map<String, LoginService> loggedApplication;

  /**
   * 登录应用
   */
  private final LoginService application;

  /**
   * 初始化登录凭证
   * 
   * @param ticketId      登录凭证ID
   * @param authorization 认证结果
   * @param application   登录应用
   * @param expirePolicy  过期策略
   */
  public DefaultLoginTicket(String ticketId,
      LoginAuthorization authorization,
      LoginService application,
      TokenExpirePolicy expirePolicy) {
    this(ticketId, authorization, application, expirePolicy, System.currentTimeMillis(), new HashMap<>());
  }

  /**
   * 初始化登录凭证
   * 
   * @param ticketId          登录凭证ID
   * @param authorization     认证结果
   * @param application       登录应用
   * @param expirePolicy      过期策略
   * @param createTime        创建时间
   * @param loggedApplication 已授权应用
   */
  public DefaultLoginTicket(String ticketId,
      LoginAuthorization authorization,
      LoginService application,
      TokenExpirePolicy expirePolicy,
      Long createTime,
      Map<String, LoginService> loggedApplication) {
    super(createTime);
    this.id = ticketId;
    this.authorization = authorization;
    this.application = application;
    this.expirePolicy = expirePolicy;
    this.createTime = createTime;
    this.loggedApplication = loggedApplication;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public LoginAuthorization authorization() {
    return authorization;
  }

  @Override
  public boolean isExpired() {
    return expirePolicy.isExpired(this);
  }

  @Override
  public ServiceTicket createTicket(String ticketId, LoginService application,
                                    TokenExpirePolicy expirePolicy) {
    return new DefaultServiceTicket(ticketId, application,
        expirePolicy, this);
  }

  @Override
  public Long createTime() {
    return createTime;
  }

  @Override
  public Map<String, LoginService> loggedApplication() {
    if (null == loggedApplication) {
      loggedApplication = new HashMap<>();
    }
    return loggedApplication;
  }

  /**
   * @return 计算有效时间
   */
  public Long getExpireTime() {
    HardTimeTicketExpirePolicy policy = (HardTimeTicketExpirePolicy) expirePolicy;
    return policy.getTimeToLive() + createTime;
  }

  @Override
  public LoginService application() {
    return application;
  }
}
