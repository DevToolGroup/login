package group.devtool.login.server;

import group.devtool.login.client.TokenExpirePolicy;

public class TestRegisterApplication implements RegisterApplication {

  private String validateTicketUrl;
  private String logoutUrl;
  private String publicKey;
  private String id;

  public TestRegisterApplication() {
  }

  public TestRegisterApplication(String validateTicketUrl, String logoutUrl,
      String publicKey, String id) {
    this.validateTicketUrl = validateTicketUrl;
    this.logoutUrl = logoutUrl;
    this.publicKey = publicKey;
    this.id = id;
  }

  @Override
  public String id() {
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
  public String validateTicketUrl() {
    return validateTicketUrl;
  }

  @Override
  public TokenExpirePolicy expirePolicy() {
    return null;
  }

  public void setValidateTicketUrl(String validateTicketUrl) {
    this.validateTicketUrl = validateTicketUrl;
  }

  public void setLogoutUrl(String logoutUrl) {
    this.logoutUrl = logoutUrl;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public void setId(String id) {
    this.id = id;
  }

  

}
