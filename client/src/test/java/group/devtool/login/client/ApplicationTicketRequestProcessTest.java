package group.devtool.login.client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ApplicationTicketRequestProcessTest {

  private String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJblOEgDeQgFiM9wFFKaaHjB5vUugkiSwNsz5vSCSRyE5IioPtAM+VxEsEIzWSYY54LTTdrJNYH1zz0f5wSQvW6IxFViDKiASipIWi1CFmG6mJfvVdq0UAZOWJP4kM4+FaWm7LZ+CB8oR4bX7FK1WfF7ocztAMPgTixXsIMl1xEpAgMBAAECgYAaG1r/NKCcV27J6wuNFht0pyGpMmyGl2NoGYUAMUlqdoCetzM05kW/Zb/0GDO4oG7vptTtwUmxbEC4g9xBoTQw1REC/IL7o4RVWwWDyd2wPZVyQQiXMOCIR/CkQoMpg8WbBS0uEObumSdnZrfkh51DcdzlaHpqWD0oCfjMnasrnQJBAP46yaAu5Up+/oqJ8alkcOr+q5fjYMwxIEABSWcTki8WA5ANT9Aq6O6k/GmaOcNFBR6kvNXdKBjZPyQz36Q1h8UCQQCX8jgrXxDZK5ira956fhya49b72S0JchN3LOogErsEyQMAlwBEGaEJfn6ew+edHzBlw3HagugAs6stFv45KBYVAkEAvyKDpCKd0LxZst6zCZ+yJXsCl1cj12C31mchQJW+Ohha5Vqcvu0D4ye3fc2tz9l8V+WS81cqZkQu7JDzewmj4QJAJqRgKo+Lvl86/WV6eBZ4ed+1vej2bi+HycgvZqa8zzO7wrukQq1t6fc0hnX2Alx7N3YkD1k5XWxT/SyazLhoHQJAT/Xco6TZW1Uqh0wSV0ICHexIUAJ5iIGiECRCZ1V/MAA8zCUT/UJXbUdXe8NbjQdcy+Z+sjcKitVAwMxYjTSS3Q==";

  @Before
  public void initProcess() {

    LoginClientProperties properties = new LoginClientProperties();
    properties.appTicketUrl("http://localhost/validateTicket");
    properties.privateKey(priKey);
    LoginClientConfiguration.properties(properties);

  }

  @Test
  public void testMatchTrue() {
    TestRequest request = new TestRequest();
    request.setUri("/validateTicket");
    request.setUrl("http://localhost/validateTicket");

    ApplicationTicketRequestProcess process = new ApplicationTicketRequestProcess(new RequestResponseProcessTest());
    boolean result = process.match(request);
    Assert.assertTrue(result);
  }

  @Test
  public void testMatchFalse() {
    TestRequest request = new TestRequest();
    request.setUri("/validateTicket");
    request.setUrl("http://localhost/2validateTicket");

    ApplicationTicketRequestProcess process = new ApplicationTicketRequestProcess(new RequestResponseProcessTest());
    boolean result = process.match(request);
    Assert.assertFalse(result);
  }

  @Test
  public void testProcessSuccess() {
    TokenSerializer mockSerializer = mock(TokenSerializer.class);
    LoginClientConfiguration.serializer(mockSerializer);

    TokenRepository mockRepository = mock(TokenRepository.class);
    LoginClientConfiguration.repository(mockRepository);

    HttpClient mockHttpClient = mock(HttpClient.class);
    LoginClientConfiguration.client(mockHttpClient);

    ApplicationTicketRequestProcess process = new ApplicationTicketRequestProcess(new RequestResponseProcessTest());

    try {
      LoginTokenTest token = new LoginTokenTest("123",
          new LoginAuthorizationTest("123"),
          new LoginApplicationTest("http://localhost/", "123"));

      when(mockHttpClient.execute(any())).thenReturn("success");
      when(mockSerializer.deserialize(any())).thenReturn(token);
      when(mockRepository.create(any())).thenReturn(new LoginCookie("x-session-id", "123", true));

      TestRequest request = new TestRequest();
      request.setUrl("http://localhost/validateTicket?appTicket=123");

      TestResponse response = new TestResponse();
      process.process(request, response);

      Assert.assertEquals(response.getRedirectUrl(), "http://localhost/");

    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
  }

  @Test
  public void testProcessValidateException() { 
    TokenSerializer mockSerializer = mock(TokenSerializer.class);
    LoginClientConfiguration.serializer(mockSerializer);

    TokenRepository mockRepository = mock(TokenRepository.class);
    LoginClientConfiguration.repository(mockRepository);

    HttpClient mockHttpClient = mock(HttpClient.class);
    LoginClientConfiguration.client(mockHttpClient);

    ApplicationTicketRequestProcess process = new ApplicationTicketRequestProcess(new RequestResponseProcessTest());

    try {
      LoginTokenTest token = new LoginTokenTest("123",
          new LoginAuthorizationTest("123"),
          new LoginApplicationTest("http://localhost/", "123"));

      when(mockHttpClient.execute(any())).thenThrow(new IOException("error"));
      when(mockSerializer.deserialize(any())).thenReturn(token);
      when(mockRepository.create(any())).thenReturn(new LoginCookie("x-session-id", "123", true));

      TestRequest request = new TestRequest();
      request.setUrl("http://localhost/validateTicket?appTicket=123");

      TestResponse response = new TestResponse();
      process.process(request, response);

      Assert.assertTrue(response.getError() instanceof LoginValidateRequestException);
      Assert.assertEquals(response.getError().getMessage(), "error");

    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
  }

  @Test
  public void testProcessDeserializeException() { 
    TokenSerializer mockSerializer = mock(TokenSerializer.class);
    LoginClientConfiguration.serializer(mockSerializer);

    TokenRepository mockRepository = mock(TokenRepository.class);
    LoginClientConfiguration.repository(mockRepository);

    HttpClient mockHttpClient = mock(HttpClient.class);
    LoginClientConfiguration.client(mockHttpClient);

    ApplicationTicketRequestProcess process = new ApplicationTicketRequestProcess(new RequestResponseProcessTest());

    try {

      when(mockHttpClient.execute(any())).thenReturn("");
      when(mockSerializer.deserialize(any())).thenThrow(new LoginTokenSerializeException("error"));
      when(mockRepository.create(any())).thenReturn(new LoginCookie("x-session-id", "123", true));

      TestRequest request = new TestRequest();
      request.setUrl("http://localhost/validateTicket?appTicket=123");

      TestResponse response = new TestResponse();
      process.process(request, response);

      Assert.assertTrue(response.getError() instanceof LoginValidateRequestException);

    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
  }
}
