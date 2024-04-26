package group.devtool.login.server;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

import group.devtool.login.client.ApplicationAuthorizeArgument;
import group.devtool.login.client.InvalidLoginAuthorizeParameterException;
import group.devtool.login.client.LoginCookie;

public class AuthenticateRequestProcessTest {

  @Test
  public void testMatch() {
    LoginServerProperties properties = new LoginServerProperties();
    properties.authenticateUrl("http://server/authenticate");
    LoginServerConfiguration.properties(properties);

    AuthenticateRequestProcess process = new AuthenticateRequestProcess(new ServerRequestResponseProcessTest());
    TestServerRequest request = new TestServerRequest();
    request.setUrl("http://server/authenticate?redirectUrl=123");
    boolean result = process.match(request);
    Assert.assertTrue(result);
  }

  @Test
  public void testProcess() throws LoginAuthenticateException, InvalidLoginAuthorizeParameterException, LoginRestrictException {
    String originUrl = "http://server/biz";
    String requestUrl = originUrl + "?appId=123";
    String authorizeUrl = "http://server/authorize?redirectUrl=" + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8);
    String authenticateUrl = "http://server/authenticate?redirectUrl=" + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8);

    IdGenerator idGenerator = mock(IdGenerator.class);
    LoginServerConfiguration.idGenerator(idGenerator);
    LoginAuthenticate authenticate = mock(LoginAuthenticate.class);
    LoginServerConfiguration.authenticate(authenticate);
    LoginTicketFactory factory = mock(LoginTicketFactory.class);
    LoginServerConfiguration.factory(factory);
    LoginRestrictStrategy restrict = mock(LoginRestrictStrategy.class);
    LoginServerConfiguration.restrict(restrict);
    TicketRepository repository = mock(TicketRepository.class);
    LoginServerConfiguration.repository(repository);
    ApplicationAuthorizeArgument argument = mock(ApplicationAuthorizeArgument.class);
    LoginServerConfiguration.argument(argument);


    when(authenticate.authenticate(any())).thenReturn(new LoginAuthorizationResult(authorizeUrl, new TestLoginAuthorization("account")));
    when(argument.resolve(anyString())).thenReturn(new TestLoginApplication("123", originUrl));
    when(factory.create(anyString(), any(), any(), any())).thenReturn(new TestLoginTicket());
    when(idGenerator.nextId()).thenReturn("123");
    doNothing().when(restrict).restrict(any());
    doNothing().when(repository).saveTicket(any());
    when(repository.create(any())).thenReturn(new LoginCookie("name", "value", false));

    AuthenticateRequestProcess process = new AuthenticateRequestProcess(new ServerRequestResponseProcessTest());

    TestServerRequest request = new TestServerRequest();
    request.setUrl(authenticateUrl);
    TestServerResponse response = new TestServerResponse();
    process.process(request, response);

    Assert.assertEquals(authorizeUrl, response.getRedirectUrl());
  }
}
