package group.devtool.login;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

public class ApplicationAuthorizeRequestProcessTest {

  public String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW5ThIA3kIBYjPcBRSmmh4web1LoJIksDbM+b0gkkchOSIqD7QDPlcRLBCM1kmGOeC003ayTWB9c89H+cEkL1uiMRVYgyogEoqSFotQhZhupiX71XatFAGTliT+JDOPhWlpuy2fggfKEeG1+xStVnxe6HM7QDD4E4sV7CDJdcRKQIDAQAB";

  private String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJblOEgDeQgFiM9wFFKaaHjB5vUugkiSwNsz5vSCSRyE5IioPtAM+VxEsEIzWSYY54LTTdrJNYH1zz0f5wSQvW6IxFViDKiASipIWi1CFmG6mJfvVdq0UAZOWJP4kM4+FaWm7LZ+CB8oR4bX7FK1WfF7ocztAMPgTixXsIMl1xEpAgMBAAECgYAaG1r/NKCcV27J6wuNFht0pyGpMmyGl2NoGYUAMUlqdoCetzM05kW/Zb/0GDO4oG7vptTtwUmxbEC4g9xBoTQw1REC/IL7o4RVWwWDyd2wPZVyQQiXMOCIR/CkQoMpg8WbBS0uEObumSdnZrfkh51DcdzlaHpqWD0oCfjMnasrnQJBAP46yaAu5Up+/oqJ8alkcOr+q5fjYMwxIEABSWcTki8WA5ANT9Aq6O6k/GmaOcNFBR6kvNXdKBjZPyQz36Q1h8UCQQCX8jgrXxDZK5ira956fhya49b72S0JchN3LOogErsEyQMAlwBEGaEJfn6ew+edHzBlw3HagugAs6stFv45KBYVAkEAvyKDpCKd0LxZst6zCZ+yJXsCl1cj12C31mchQJW+Ohha5Vqcvu0D4ye3fc2tz9l8V+WS81cqZkQu7JDzewmj4QJAJqRgKo+Lvl86/WV6eBZ4ed+1vej2bi+HycgvZqa8zzO7wrukQq1t6fc0hnX2Alx7N3YkD1k5XWxT/SyazLhoHQJAT/Xco6TZW1Uqh0wSV0ICHexIUAJ5iIGiECRCZ1V/MAA8zCUT/UJXbUdXe8NbjQdcy+Z+sjcKitVAwMxYjTSS3Q==";

  @Test
  public void testProcessNotLogin() throws UnsupportedEncodingException {
    String requestUrl = "http://client?param=123&appId=123";
    String authorizeUrl = "http://server/authorize?redirectUrl="
        + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8.name());
    String authenticateUrl = "http://server/authenticateUrl?redirectUrl="
        + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8.name());
    String loginUrl = "http://login/redirectUrl=" + URLEncoder.encode(authenticateUrl, StandardCharsets.UTF_8.name());

    LoginServerConfiguration.protocol(new LoginServerProtocolFactory());

    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);

    ServiceRepository applications = mock(ServiceRepository.class);
    LoginServerConfiguration.applications(applications);

    LoginTicketManager repository = mock(LoginTicketManager.class);
    LoginServerConfiguration.manager(repository);
    when(repository.resolve(any())).thenReturn(null);

    TicketIdGenerator idGenerator = mock(TicketIdGenerator.class);
    LoginServerConfiguration.idGenerator(idGenerator);

    LoginAuthenticate authenticate = mock(LoginAuthenticate.class);
    LoginServerConfiguration.authenticate(authenticate);
    when(authenticate.loginUrl(any())).thenReturn(new LoginRedirectResponse() {

      @Override
      public String getLocation() {
        return loginUrl;
      }

    });

    ServiceAuthorizeRequestProcess process = new ServiceAuthorizeRequestProcess(
        new ServerRequestResponseProcessTest());

    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl(requestUrl);
    request.setQuery(new HashMap<>());
    request.query().put("appid", "123");
    request.query().put("requestUrl", requestUrl);
    request.setCookies(new HashMap<>());

    process.process(request, response);
    Assert.assertEquals(loginUrl, response.getRedirectUrl());
  }

  @Test
  public void testProcessLogged() throws LoginException, UnsupportedEncodingException {
    String requestUrl = "http://client?param=123";
    String authorizeUrl = "http://server/authorize?appid=123&redirectUrl="
        + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8.name());
    String authenticateUrl = "http://server/authenticateUrl?appid=123&redirectUrl="
        + URLEncoder.encode(requestUrl, StandardCharsets.UTF_8.name());
    String loginUrl = "http://login/redirectUrl=" + URLEncoder.encode(authenticateUrl, StandardCharsets.UTF_8.name());
    String clientTicketUrl = "http://client/validateTicket";
    String redirectClientTicketUrl = "http://client/validateTicket?appTicket=456";

    LoginServerConfiguration.protocol(new LoginServerProtocolFactory());

    ServiceRepository applications = mock(ServiceRepository.class);
    LoginServerConfiguration.applications(applications);
    when(applications.getApplication(anyString()))
        .thenReturn(new DefaultRegisterService("client", pubKey, null, clientTicketUrl, 10000L));

    LoginTicketManager manager = mock(LoginTicketManager.class);
    LoginServerConfiguration.manager(manager);

    DefaultLoginAuthorization authorization = new DefaultLoginAuthorization("123", new HashMap<>());
    DefaultLoginService loginApplication = new DefaultLoginService("123", requestUrl);
    HardTimeTicketExpirePolicy policy = new HardTimeTicketExpirePolicy(100000L);
    DefaultLoginTicket loginTicket = new DefaultLoginTicket("id", authorization, loginApplication, policy);

    when(manager.resolve(any())).thenReturn(loginTicket);
    when(manager.synchronize(any(), any()))
        .thenReturn(new DefaultServiceTicket("456", loginApplication, policy, loginTicket));

    TicketIdGenerator idGenerator = mock(TicketIdGenerator.class);
    LoginServerConfiguration.idGenerator(idGenerator);
    when(idGenerator.nextId()).thenReturn("456");

    LoginAuthenticate authenticate = mock(LoginAuthenticate.class);
    LoginServerConfiguration.authenticate(authenticate);
    when(authenticate.loginUrl(any())).thenReturn(new LoginRedirectResponse() {

      @Override
      public String getLocation() {
        return loginUrl;
      }

    });

    ServiceAuthorizeRequestProcess process = new ServiceAuthorizeRequestProcess(
        new ServerRequestResponseProcessTest());

    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl(authorizeUrl);
    request.setQuery(new HashMap<>());
    request.query().put("appid", "123");
    request.query().put("requestUrl", requestUrl);
    request.setCookies(new HashMap<>());

    process.process(request, response);
    System.out.println(response.getRedirectUrl());
    Assert.assertEquals(redirectClientTicketUrl, response.getRedirectUrl());
  }
}
