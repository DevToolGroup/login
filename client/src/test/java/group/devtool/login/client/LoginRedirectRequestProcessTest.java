package group.devtool.login.client;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Test;

public class LoginRedirectRequestProcessTest {

  @Test
  public void testProcess() {
    ApplicationAuthorizeArgument mockArgument = mock(ApplicationAuthorizeArgument.class);
    LoginClientConfiguration.argument(mockArgument);

    LoginClientProperties properties = new LoginClientProperties();
    properties.authorizeUrl("http://server.com/authorize");
    properties.appId("123");
    LoginClientConfiguration.properties(properties);

    LoginRedirectRequestProcess process = new LoginRedirectRequestProcess(new RequestResponseProcessTest());
    TestRequest request = new TestRequest();
    request.setUrl("http://client.com/db?param=123");

    String requestUrl = "http://client.com/db?param=123&appid=123";
    TestResponse response = new TestResponse();
    when(mockArgument.construct(anyString(), anyString())).thenReturn(requestUrl);
    process.process(request, response);

    String expect = "http://server.com/authorize?redirectUrl=" + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8);
    Assert.assertEquals(expect, response.getRedirectUrl());
  }
}
