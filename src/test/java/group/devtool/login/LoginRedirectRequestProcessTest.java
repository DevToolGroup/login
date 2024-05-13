package group.devtool.login;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

public class LoginRedirectRequestProcessTest {

  @Test
  public void testProcess() throws UnsupportedEncodingException {
    LoginClientConfiguration.protocol(new LoginClientProtocolFactory());

    LoginRedirection mockArgument = mock(LoginRedirection.class);
    LoginClientConfiguration.redirection(mockArgument);

    LoginClientProperties properties = new LoginClientProperties();
    properties.serverAuthorizeUrl("http://server/authorize");
    properties.appId("123");
    LoginClientConfiguration.properties(properties);

    LoginRedirectRequestProcess process = new LoginRedirectRequestProcess(new RequestResponseProcessTest());
    TestRequest request = new TestRequest();
    request.setUrl("http://client/db?param=123");

    String requestUrl = "http://client/db?param=123";
    TestResponse response = new TestResponse();
    when(mockArgument.loggedRedirectUrl(any())).thenReturn(requestUrl);
    process.process(request, response);

    String expect = "http://server/authorize?appid=123&redirectUrl=" + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8.name());
    Assert.assertEquals(expect, response.getRedirectUrl());
  }
}
