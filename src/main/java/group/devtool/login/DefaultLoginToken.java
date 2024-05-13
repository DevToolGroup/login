package group.devtool.login;

/**
 * 默认实现，登录状态
 */
public class DefaultLoginToken implements LoginToken {

  private static final long serialVersionUID = -3649268788492362383L;

  /**
   * 登录标识
   */
  private String id;

  /**
   * 认证结果
   */
  private LoginAuthorization authorization;

  /**
   * 过期时间
   */
  private Long expireTime;

  /**
   * 登录应用
   */
  private LoginService application;

  /**
   * 初始化登录状态
   * 
   * @param id            登录状态标识
   * @param authorization 认证结果
   * @param application   登录应用
   * @param expireTime    过期时间
   */
  public DefaultLoginToken(String id,
      LoginAuthorization authorization,
      LoginService application,
      Long expireTime) {
    this.id = id;
    this.authorization = authorization;
    this.expireTime = expireTime;
    this.application = application;
  }  

  @Override
  public String id() {
    return id;
  }

  @Override
  public LoginAuthorization authorization() {
    return authorization;
  }

  /**
   * @return 获取过期时间
   */
  public Long getExpireTime() {
    return expireTime;
  }

  @Override
  public boolean isExpired() {
    return expireTime < System.currentTimeMillis();
  }

  @Override
  public LoginService application() {
    return application;
  }

}
