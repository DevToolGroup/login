package group.devtool.login.client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

public class ClientLogoutRequestProcessTest {

  @Test
  public void testProcess() {
    String serverLogoutUrl = "http://server/login/server/logout";
    String logoutRedirectUrl = "http://client/login";

    LoginClientProperties loginClientProperties = new LoginClientProperties();
    loginClientProperties.serverLogoutUrl(serverLogoutUrl);    
    LoginClientConfiguration.properties(loginClientProperties);

    TokenRepository repository = mock(TokenRepository.class);
    LoginClientConfiguration.repository(repository);

    ClientLogoutRequestProcess process = new ClientLogoutRequestProcess(new RequestResponseProcessTest());
    TestRequest request = new TestRequest();
    request.setUrl("http://client/logout");
    request.setLogoutRedirectUrl(logoutRedirectUrl);

    TestResponse response = new TestResponse();

    when(repository.resolve(any())).thenReturn(new LoginTokenTest("123", null, null));
    doNothing().when(repository).clean(anyString());
    process.process(request, response);

    Assert.assertEquals(serverLogoutUrl + "?redirectUrl=" + URLEncoder.encode(logoutRedirectUrl, StandardCharsets.UTF_8), response.getRedirectUrl());
  }

  @Test
  public void testProcessException() {
    String serverLogoutUrl = "http://server/login/server/logout";
    String logoutRedirectUrl = "http://client/login";

    LoginClientProperties loginClientProperties = new LoginClientProperties();
    loginClientProperties.serverLogoutUrl(serverLogoutUrl);    
    LoginClientConfiguration.properties(loginClientProperties);

    TokenRepository repository = mock(TokenRepository.class);
    LoginClientConfiguration.repository(repository);

    ClientLogoutRequestProcess process = new ClientLogoutRequestProcess(new RequestResponseProcessTest());
    TestRequest request = new TestRequest();
    request.setUrl("http://client/logout");
    request.setLogoutRedirectUrl(logoutRedirectUrl);

    TestResponse response = new TestResponse();

    when(repository.resolve(any())).thenReturn(null);
    doNothing().when(repository).clean(anyString());
    process.process(request, response);

    Assert.assertEquals(serverLogoutUrl + "?redirectUrl=" + URLEncoder.encode(logoutRedirectUrl, StandardCharsets.UTF_8), response.getRedirectUrl());
  }
}
