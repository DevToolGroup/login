package group.devtool.login.server;

import java.util.Map;

import group.devtool.login.client.LoginApplication;
import group.devtool.login.client.LoginAuthorization;
import group.devtool.login.client.TokenExpirePolicy;

public class TestLoginTicket implements LoginTicket {

  private static final long serialVersionUID = -6948717705582975756L;

  private String id;

  private LoginAuthorization authorization;

  private LoginApplication application;

  private Map<String, LoginApplication> loggedApplication;


  public TestLoginTicket() {
  }

  public TestLoginTicket(String id, LoginAuthorization authorization, LoginApplication application,
      Map<String, LoginApplication> loggedApplication) {
    this.id = id;
    this.authorization = authorization;
    this.application = application;
    this.loggedApplication = loggedApplication;
  }

  @Override
  public String id() {
    return id;
  }

  @Override
  public boolean isExpired() {
    return false;
  }

  @Override
  public LoginAuthorization authorization() {
    return authorization;
  }

  @Override
  public LoginApplication application() {
    return application;
  }

  @Override
  public Map<String, LoginApplication> loggedApplication() {
    return loggedApplication;
  }

  @Override
  public ApplicationTicket createTicket(String ticketId, LoginApplication application, TokenExpirePolicy expirePolicy) {
    return new TestApplicationTicket(ticketId, application, this);
  }

}
