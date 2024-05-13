package group.devtool.login;

import java.util.HashMap;

public class TestLoginAuthenticate implements LoginAuthenticate {

  private String redirectUrl;

  public TestLoginAuthenticate(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  @Override
  public LoginRedirectResponse loginUrl(LoginRedirectResponse redirectResponse) {
    return redirectResponse;
  }

  @Override
  public <Q> LoginAuthorizationResult doAuthenticate(Q request) throws LoginAuthenticateException {
    return new TestLoginAuthorizationResult(new DefaultLoginAuthorization("user1", new HashMap<>()),
        new DefaultLoginService("client", redirectUrl));
  }

}
