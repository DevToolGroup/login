package group.devtool.login;

public class TestLoginTokenEntity {

  private String id;

  private LoginAuthorizationEntityImpl authorization;

  private LoginApplicationEntityImpl application;

  private Long expireTime;

  public String getId() {
    return id;
  }

  public LoginAuthorizationEntityImpl getAuthorization() {
    return authorization;
  }

  public Long getExpireTime() {
    return expireTime;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setAuthorization(LoginAuthorizationEntityImpl authorization) {
    this.authorization = authorization;
  }

  public void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }

  public LoginApplicationEntityImpl getApplication() {
    return application;
  }

  public void setApplication(LoginApplicationEntityImpl application) {
    this.application = application;
  }

}
