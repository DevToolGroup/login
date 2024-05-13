package group.devtool.login;

/**
 * 应用凭证默认实现。在实际场景中，可以通过实现ApplicationTicket自定义应用登录凭证
 */
public class DefaultServiceTicket extends TimeTrackable implements ServiceTicket {

  private static final long serialVersionUID = 9156189699240953257L;

  /**
   * 凭证标识
   */
  private String id;

  /**
   * 登录应用
   */
  private LoginService application;

  /**
   * 过期策略
   */
  private TokenExpirePolicy expirePolicy;

  /**
   * 创建时间
   */
  private Long createTime;

  /**
   * 登录凭证
   */
  private DefaultLoginTicket loginTicket;

  /**
   * 确认时间
   */
  private long confirmTime;

  /**
   * 初始化应用登录凭证
   * 
   * @param ticketId     凭证ID
   * @param application  登录应用
   * @param expirePolicy 过期策略
   * @param loginTicket  登录凭证
   */
  public DefaultServiceTicket(String ticketId,
      LoginService application,
      TokenExpirePolicy expirePolicy,
      DefaultLoginTicket loginTicket) {
    this(ticketId,
        application,
        expirePolicy,
        System.currentTimeMillis(),
        loginTicket);
  }

  /**
   * 初始化应用登录凭证
   * 
   * @param ticketId     登录凭证ID
   * @param application  登录应用
   * @param expirePolicy 过期策略
   * @param createTime   创建时间
   * @param loginTicket  登录凭证
   */
  public DefaultServiceTicket(String ticketId,
      LoginService application,
      TokenExpirePolicy expirePolicy,
      Long createTime,
      DefaultLoginTicket loginTicket) {
    super(createTime);
    this.id = ticketId;
    this.application = application;
    this.expirePolicy = expirePolicy;
    this.createTime = createTime;
    this.loginTicket = loginTicket;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public DefaultLoginTicket loginTicket() {
    return loginTicket;
  }

  @Override
  public boolean isExpired() {
    return expirePolicy.isExpired(this);
  }

  @Override
  public LoginToken confirm() {
    confirmTime = System.currentTimeMillis();
    return new DefaultLoginToken(id,
        loginTicket.authorization(),
        application,
        loginTicket.getExpireTime());
  }

  @Override
  public Long createTime() {
    return createTime;
  }

  /**
   * @return 获取过期时间
   */
  public Long getExpireTime() {
    HardTimeTicketExpirePolicy policy = (HardTimeTicketExpirePolicy) expirePolicy;
    return policy.getTimeToLive() + createTime;
  }

  /**
   * @return 确认时间
   */
  public long getConfirmTime() {
    return confirmTime;
  }

  @Override
  public LoginService service() {
    return application;
  }
}
