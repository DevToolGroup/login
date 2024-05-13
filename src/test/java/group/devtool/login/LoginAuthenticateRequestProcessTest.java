package group.devtool.login;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

public class LoginAuthenticateRequestProcessTest {

  @Test
  public void testMatch() {
    LoginServerProperties properties = new LoginServerProperties();
    properties.authenticateUrl("http://server/authenticate");
    properties.authorizeUrl("http://server/authorize");
    LoginServerConfiguration.properties(properties);

    LoginAuthenticateRequestProcess process = new LoginAuthenticateRequestProcess(
        new ServerRequestResponseProcessTest());
    TestServerRequest request = new TestServerRequest();
    request.setUrl("http://server/authenticate?redirectUrl=123");
    boolean result = process.match(request);
    Assert.assertTrue(result);
  }

  @Test
  public void testProcess()
      throws LoginAuthenticateException, InvalidLoginAuthorizeParameterException, LoginRestrictException, UnsupportedEncodingException {
    String originUrl = "http://server/biz";
    String authorizeUrl = "http://server/authorize?appid=client&redirectUrl="
        + URLEncoder.encode(originUrl, StandardCharsets.UTF_8.name());
    String authenticateUrl = "http://server/authenticate?appid=client&redirectUrl="
        + URLEncoder.encode(authorizeUrl, StandardCharsets.UTF_8.name());

    LoginServerProperties properties = new LoginServerProperties();
    properties.authenticateUrl("http://server/authenticate");
    properties.authorizeUrl("http://server/authorize");
    LoginServerConfiguration.properties(properties);
    LoginServerConfiguration.protocol(new LoginServerProtocolFactory());

    TicketIdGenerator idGenerator = mock(TicketIdGenerator.class);
    LoginServerConfiguration.idGenerator(idGenerator);
    LoginAuthenticate authenticate = mock(LoginAuthenticate.class);
    LoginServerConfiguration.authenticate(authenticate);
    LoginTicketFactory factory = mock(LoginTicketFactory.class);
    LoginServerConfiguration.factory(factory);
    LoginRestrictStrategy restrict = mock(LoginRestrictStrategy.class);
    LoginServerConfiguration.restrict(restrict);
    LoginTicketManager repository = mock(LoginTicketManager.class);
    LoginServerConfiguration.manager(repository);

    DefaultLoginAuthorization authorization = new DefaultLoginAuthorization("user1", new HashMap<>());
    DefaultLoginService loginApplication = new DefaultLoginService("client", originUrl);
    TestLoginAuthorizationResult result = new TestLoginAuthorizationResult(authorization, loginApplication);
    TokenExpirePolicy policy = new HardTimeTicketExpirePolicy(10000L);

    when(authenticate.authenticate(any())).thenReturn(result);
    when(factory.create(anyString(), any(), any(), any())).thenReturn(new DefaultLoginTicket("123", authorization, loginApplication, policy));
    when(idGenerator.nextId()).thenReturn("123");
    doNothing().when(restrict).restrict(any());
    when(repository.saveOrUpdate(any(), any())).thenReturn(new LoginCookie("name", "value", false));

    LoginAuthenticateRequestProcess process = new LoginAuthenticateRequestProcess(
        new ServerRequestResponseProcessTest());

    TestServerRequest request = new TestServerRequest();
    request.setUrl(authenticateUrl);

    TestServerResponse response = new TestServerResponse();
    process.process(request, response);

    Assert.assertEquals(authorizeUrl, response.getRedirectUrl());
  }
}
