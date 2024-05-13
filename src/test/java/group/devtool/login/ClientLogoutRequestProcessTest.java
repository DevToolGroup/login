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

public class ClientLogoutRequestProcessTest {

  @Test
  public void testProcess() throws UnsupportedEncodingException {
    String serverLogoutUrl = "http://server/login/server/logout";
    String logoutRedirectUrl = "http://client/login";

    LoginClientConfiguration.protocol(new LoginClientProtocolFactory());

    LoginClientProperties loginClientProperties = new LoginClientProperties();
    loginClientProperties.serverLogoutUrl(serverLogoutUrl);  
    loginClientProperties.appId("123");  
    LoginClientConfiguration.properties(loginClientProperties);

    LoginTokenManager repository = mock(LoginTokenManager.class);
    LoginClientConfiguration.manager(repository);

    LoginRedirection construct = mock(LoginRedirection.class);
    LoginClientConfiguration.redirection(construct);
    ClientLogoutRequestProcess process = new ClientLogoutRequestProcess(new RequestResponseProcessTest());
    TestRequest request = new TestRequest();
    request.setUrl("http://client/logout");
    request.setLogoutRedirectUrl(logoutRedirectUrl);

    TestResponse response = new TestResponse();

    when(repository.resolve(any())).thenReturn(new DefaultLoginToken("123", new DefaultLoginAuthorization("user1", new HashMap<>()), new DefaultLoginService("client", "http://client"), System.currentTimeMillis() + 100000));
    doNothing().when(repository).clean(anyString());

    when(construct.logoutRedirectUrl(any())).thenReturn(logoutRedirectUrl);
    process.process(request, response);

    Assert.assertEquals(serverLogoutUrl + "?appid=123&redirectUrl=" + URLEncoder.encode(logoutRedirectUrl, StandardCharsets.UTF_8.name()), response.getRedirectUrl());
  }

  @Test
  public void testProcessException() throws UnsupportedEncodingException {
    String serverLogoutUrl = "http://server/login/server/logout";
    String logoutRedirectUrl = "http://client/login";

    LoginClientConfiguration.protocol(new LoginClientProtocolFactory());

    LoginClientProperties loginClientProperties = new LoginClientProperties();
    loginClientProperties.serverLogoutUrl(serverLogoutUrl);
    loginClientProperties.appId("123");
    LoginClientConfiguration.properties(loginClientProperties);

    LoginTokenManager repository = mock(LoginTokenManager.class);
    LoginClientConfiguration.manager(repository);

    LoginRedirection construct = mock(LoginRedirection.class);
    LoginClientConfiguration.redirection(construct);

    ClientLogoutRequestProcess process = new ClientLogoutRequestProcess(new RequestResponseProcessTest());
    TestRequest request = new TestRequest();
    request.setUrl("http://client/logout");
    request.setLogoutRedirectUrl(logoutRedirectUrl);

    TestResponse response = new TestResponse();

    when(repository.resolve(any())).thenReturn(null);
    doNothing().when(repository).clean(anyString());

    when(construct.logoutRedirectUrl(any())).thenReturn(logoutRedirectUrl);
    process.process(request, response);

    Assert.assertEquals(serverLogoutUrl + "?appid=123&redirectUrl="
        + URLEncoder.encode(logoutRedirectUrl, StandardCharsets.UTF_8.name()), response.getRedirectUrl());
  }
}
