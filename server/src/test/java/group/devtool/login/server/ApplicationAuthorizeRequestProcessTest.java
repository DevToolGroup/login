package group.devtool.login.server;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import group.devtool.login.client.ApplicationAuthorizeArgument;
import group.devtool.login.client.InvalidLoginAuthorizeParameterException;

public class ApplicationAuthorizeRequestProcessTest {

  public String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW5ThIA3kIBYjPcBRSmmh4web1LoJIksDbM+b0gkkchOSIqD7QDPlcRLBCM1kmGOeC003ayTWB9c89H+cEkL1uiMRVYgyogEoqSFotQhZhupiX71XatFAGTliT+JDOPhWlpuy2fggfKEeG1+xStVnxe6HM7QDD4E4sV7CDJdcRKQIDAQAB";

  private String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJblOEgDeQgFiM9wFFKaaHjB5vUugkiSwNsz5vSCSRyE5IioPtAM+VxEsEIzWSYY54LTTdrJNYH1zz0f5wSQvW6IxFViDKiASipIWi1CFmG6mJfvVdq0UAZOWJP4kM4+FaWm7LZ+CB8oR4bX7FK1WfF7ocztAMPgTixXsIMl1xEpAgMBAAECgYAaG1r/NKCcV27J6wuNFht0pyGpMmyGl2NoGYUAMUlqdoCetzM05kW/Zb/0GDO4oG7vptTtwUmxbEC4g9xBoTQw1REC/IL7o4RVWwWDyd2wPZVyQQiXMOCIR/CkQoMpg8WbBS0uEObumSdnZrfkh51DcdzlaHpqWD0oCfjMnasrnQJBAP46yaAu5Up+/oqJ8alkcOr+q5fjYMwxIEABSWcTki8WA5ANT9Aq6O6k/GmaOcNFBR6kvNXdKBjZPyQz36Q1h8UCQQCX8jgrXxDZK5ira956fhya49b72S0JchN3LOogErsEyQMAlwBEGaEJfn6ew+edHzBlw3HagugAs6stFv45KBYVAkEAvyKDpCKd0LxZst6zCZ+yJXsCl1cj12C31mchQJW+Ohha5Vqcvu0D4ye3fc2tz9l8V+WS81cqZkQu7JDzewmj4QJAJqRgKo+Lvl86/WV6eBZ4ed+1vej2bi+HycgvZqa8zzO7wrukQq1t6fc0hnX2Alx7N3YkD1k5XWxT/SyazLhoHQJAT/Xco6TZW1Uqh0wSV0ICHexIUAJ5iIGiECRCZ1V/MAA8zCUT/UJXbUdXe8NbjQdcy+Z+sjcKitVAwMxYjTSS3Q==";

  @Test
  public void testProcessNotLogin() {
    String requestUrl = "http://client?param=123&appId=123";
    String authorizeUrl = "http://server/authorize?redirectUrl=" + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8);
    String authenticateUrl = "http://server/authenticateUrl?redirectUrl=" + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8);
    String loginUrl = "http://login/redirectUrl=" + URLEncoder.encode(authenticateUrl, StandardCharsets.UTF_8);

    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);

    ApplicationRepository applications = mock(ApplicationRepository.class);
    LoginServerConfiguration.applications(applications);

    TicketRepository repository = mock(TicketRepository.class);
    LoginServerConfiguration.repository(repository);
    when(repository.resolve(any())).thenReturn(null);

    IdGenerator idGenerator = mock(IdGenerator.class);
    LoginServerConfiguration.idGenerator(idGenerator);
    
    ApplicationAuthorizeArgument argument = mock(ApplicationAuthorizeArgument.class);
    LoginServerConfiguration.argument(argument);

    LoginAuthenticate authenticate = mock(LoginAuthenticate.class);
    LoginServerConfiguration.authenticate(authenticate);
    when(authenticate.loginUrl(anyString())).thenReturn(loginUrl);


    ApplicationAuthorizeRequestProcess process = new ApplicationAuthorizeRequestProcess(
        new ServerRequestResponseProcessTest());

    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl(requestUrl);
    request.setCookies(new HashMap<>());

    process.process(request, response);
    System.out.println(response.getRedirectUrl());
    Assert.assertEquals(loginUrl, response.getRedirectUrl());
  }

  @Test
  public void testProcessLogged() throws InvalidLoginAuthorizeParameterException {
    String requestUrl = "http://client?param=123&appId=123";
    String authorizeUrl = "http://server/authorize?redirectUrl=" + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8);
    String authenticateUrl = "http://server/authenticateUrl?redirectUrl=" + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8);
    String loginUrl = "http://login/redirectUrl=" + URLEncoder.encode(authenticateUrl, StandardCharsets.UTF_8);
    String clientTicketUrl = "http://client/validateTicket";
    String redirectClientTicketUrl = "http://client/validateTicket?appTicket=456";

    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);

    ApplicationRepository applications = mock(ApplicationRepository.class);
    LoginServerConfiguration.applications(applications);
    when(applications.getApplication(anyString())).thenReturn(new TestRegisterApplication(clientTicketUrl, null, pubKey, "123"));

    TicketRepository repository = mock(TicketRepository.class);
    LoginServerConfiguration.repository(repository);
    when(repository.resolve(any())).thenReturn(new TestLoginTicket("id", new TestLoginAuthorization("123"), null, new HashMap<>()));
    doNothing().when(repository).saveTicket(any());

    IdGenerator idGenerator = mock(IdGenerator.class);
    LoginServerConfiguration.idGenerator(idGenerator);
    when(idGenerator.nextId()).thenReturn("456");
    
    ApplicationAuthorizeArgument argument = mock(ApplicationAuthorizeArgument.class);
    LoginServerConfiguration.argument(argument);
    when(argument.resolve(anyString())).thenReturn(new TestLoginApplication(requestUrl, "456"));

    LoginAuthenticate authenticate = mock(LoginAuthenticate.class);
    LoginServerConfiguration.authenticate(authenticate);
    when(authenticate.loginUrl(anyString())).thenReturn(loginUrl);


    ApplicationAuthorizeRequestProcess process = new ApplicationAuthorizeRequestProcess(
        new ServerRequestResponseProcessTest());

    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl(authorizeUrl);
    request.setCookies(new HashMap<>());

    process.process(request, response);
    System.out.println(response.getRedirectUrl());
    Assert.assertEquals(redirectClientTicketUrl, response.getRedirectUrl());
  }
}
