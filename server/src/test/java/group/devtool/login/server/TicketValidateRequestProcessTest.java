package group.devtool.login.server;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import group.devtool.login.client.LoginTokenSerializeException;
import group.devtool.login.client.LoginUtils;
import group.devtool.login.client.TokenSerializer;


public class TicketValidateRequestProcessTest {

  public String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW5ThIA3kIBYjPcBRSmmh4web1LoJIksDbM+b0gkkchOSIqD7QDPlcRLBCM1kmGOeC003ayTWB9c89H+cEkL1uiMRVYgyogEoqSFotQhZhupiX71XatFAGTliT+JDOPhWlpuy2fggfKEeG1+xStVnxe6HM7QDD4E4sV7CDJdcRKQIDAQAB";

  private String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJblOEgDeQgFiM9wFFKaaHjB5vUugkiSwNsz5vSCSRyE5IioPtAM+VxEsEIzWSYY54LTTdrJNYH1zz0f5wSQvW6IxFViDKiASipIWi1CFmG6mJfvVdq0UAZOWJP4kM4+FaWm7LZ+CB8oR4bX7FK1WfF7ocztAMPgTixXsIMl1xEpAgMBAAECgYAaG1r/NKCcV27J6wuNFht0pyGpMmyGl2NoGYUAMUlqdoCetzM05kW/Zb/0GDO4oG7vptTtwUmxbEC4g9xBoTQw1REC/IL7o4RVWwWDyd2wPZVyQQiXMOCIR/CkQoMpg8WbBS0uEObumSdnZrfkh51DcdzlaHpqWD0oCfjMnasrnQJBAP46yaAu5Up+/oqJ8alkcOr+q5fjYMwxIEABSWcTki8WA5ANT9Aq6O6k/GmaOcNFBR6kvNXdKBjZPyQz36Q1h8UCQQCX8jgrXxDZK5ira956fhya49b72S0JchN3LOogErsEyQMAlwBEGaEJfn6ew+edHzBlw3HagugAs6stFv45KBYVAkEAvyKDpCKd0LxZst6zCZ+yJXsCl1cj12C31mchQJW+Ohha5Vqcvu0D4ye3fc2tz9l8V+WS81cqZkQu7JDzewmj4QJAJqRgKo+Lvl86/WV6eBZ4ed+1vej2bi+HycgvZqa8zzO7wrukQq1t6fc0hnX2Alx7N3YkD1k5XWxT/SyazLhoHQJAT/Xco6TZW1Uqh0wSV0ICHexIUAJ5iIGiECRCZ1V/MAA8zCUT/UJXbUdXe8NbjQdcy+Z+sjcKitVAwMxYjTSS3Q==";

  @Test
  public void testProcessInValidParameter() throws LoginTokenSerializeException {

    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);
    TicketRepository repository = mock(TicketRepository.class);
    LoginServerConfiguration.repository(repository);
    TokenSerializer serializer = mock(TokenSerializer.class);
    LoginServerConfiguration.serializer(serializer);
    ApplicationRepository applications = mock(ApplicationRepository.class);
    LoginServerConfiguration.applications(applications);


    TicketValidateRequestProcess process = new TicketValidateRequestProcess(new ServerRequestResponseProcessTest());
    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl("http://server/validateTicket");

    when(repository.getTicket(any(), eq(ApplicationTicket.class))).thenThrow(new NullPointerException());
    process.process(request, response);

    Assert.assertTrue(response.getError() instanceof InvalidTicketValidateParameterException);
  }

  @Test
  public void testProcessTicketNotFound() {
    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);
    TicketRepository repository = mock(TicketRepository.class);
    LoginServerConfiguration.repository(repository);
    TokenSerializer serializer = mock(TokenSerializer.class);
    LoginServerConfiguration.serializer(serializer);
    ApplicationRepository applications = mock(ApplicationRepository.class);
    LoginServerConfiguration.applications(applications);


    TicketValidateRequestProcess process = new TicketValidateRequestProcess(new ServerRequestResponseProcessTest());
    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl("http://server/validateTicket?appTicket=123&signTicket=123");

    when(repository.getTicket(any(), eq(ApplicationTicket.class))).thenReturn(null);
    process.process(request, response);

    Assert.assertTrue(response.getError() instanceof TicketNotFoundException);
  }

  @Test
  public void testProcessVerifyException() {
    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);
    TicketRepository repository = mock(TicketRepository.class);
    LoginServerConfiguration.repository(repository);
    TokenSerializer serializer = mock(TokenSerializer.class);
    LoginServerConfiguration.serializer(serializer);
    ApplicationRepository applications = mock(ApplicationRepository.class);
    LoginServerConfiguration.applications(applications);


    TicketValidateRequestProcess process = new TicketValidateRequestProcess(new ServerRequestResponseProcessTest());
    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl("http://server/validateTicket?appTicket=123&signTicket=123");

    when(repository.getTicket(any(), eq(ApplicationTicket.class))).thenReturn(new TestApplicationTicket("123", new TestLoginApplication("123", "123"), null));
    when(applications.getApplication(anyString())).thenReturn(new TestRegisterApplication(null,null, pubKey, "123"));
    process.process(request, response);

    Assert.assertTrue(response.getError() instanceof LoginValidateException);
  }


}
