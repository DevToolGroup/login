package group.devtool.login.server;

import group.devtool.login.client.LoginApplication;
import group.devtool.login.client.LoginToken;
import group.devtool.login.client.TokenExpirePolicy;

public class TestApplicationTicket implements ApplicationTicket {

  private static final long serialVersionUID = -5396173734591707913L;

  private String ticketId;

  private LoginApplication application;

  private TestLoginTicket testLoginTicket;


  public TestApplicationTicket(String ticketId, LoginApplication application, TestLoginTicket testLoginTicket) {
    this.ticketId = ticketId;
    this.application = application;
    this.testLoginTicket = testLoginTicket;
  }

  @Override
  public String id() {
    return ticketId;
  }

  @Override
  public boolean isExpired() {
    return false;
  }

  @Override
  public LoginApplication application() {
    return application;
  }

  @Override
  public LoginTicket loginTicket() {
    return testLoginTicket;
  }

  @Override
  public LoginToken doConfirm() {
    return new TestLoginToken(ticketId, loginTicket().authorization(), application);
  }

}
