package group.devtool.login;

import java.util.HashMap;
import org.junit.Assert;
import org.junit.Test;

public class TicketValidateRequestProcessTest {

  public String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW5ThIA3kIBYjPcBRSmmh4web1LoJIksDbM+b0gkkchOSIqD7QDPlcRLBCM1kmGOeC003ayTWB9c89H+cEkL1uiMRVYgyogEoqSFotQhZhupiX71XatFAGTliT+JDOPhWlpuy2fggfKEeG1+xStVnxe6HM7QDD4E4sV7CDJdcRKQIDAQAB";

  private String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJblOEgDeQgFiM9wFFKaaHjB5vUugkiSwNsz5vSCSRyE5IioPtAM+VxEsEIzWSYY54LTTdrJNYH1zz0f5wSQvW6IxFViDKiASipIWi1CFmG6mJfvVdq0UAZOWJP4kM4+FaWm7LZ+CB8oR4bX7FK1WfF7ocztAMPgTixXsIMl1xEpAgMBAAECgYAaG1r/NKCcV27J6wuNFht0pyGpMmyGl2NoGYUAMUlqdoCetzM05kW/Zb/0GDO4oG7vptTtwUmxbEC4g9xBoTQw1REC/IL7o4RVWwWDyd2wPZVyQQiXMOCIR/CkQoMpg8WbBS0uEObumSdnZrfkh51DcdzlaHpqWD0oCfjMnasrnQJBAP46yaAu5Up+/oqJ8alkcOr+q5fjYMwxIEABSWcTki8WA5ANT9Aq6O6k/GmaOcNFBR6kvNXdKBjZPyQz36Q1h8UCQQCX8jgrXxDZK5ira956fhya49b72S0JchN3LOogErsEyQMAlwBEGaEJfn6ew+edHzBlw3HagugAs6stFv45KBYVAkEAvyKDpCKd0LxZst6zCZ+yJXsCl1cj12C31mchQJW+Ohha5Vqcvu0D4ye3fc2tz9l8V+WS81cqZkQu7JDzewmj4QJAJqRgKo+Lvl86/WV6eBZ4ed+1vej2bi+HycgvZqa8zzO7wrukQq1t6fc0hnX2Alx7N3YkD1k5XWxT/SyazLhoHQJAT/Xco6TZW1Uqh0wSV0ICHexIUAJ5iIGiECRCZ1V/MAA8zCUT/UJXbUdXe8NbjQdcy+Z+sjcKitVAwMxYjTSS3Q==";

  @Test
  public void testProcessTicketNotFoundException() {
    LoginServerConfiguration.protocol(new LoginServerProtocolFactory());

    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);

    LoginTicketManager repository = new MemoryTicketRepository();
    LoginServerConfiguration.manager(repository);

    ServiceRepository applications = new TestApplicationRepository();
    LoginServerConfiguration.applications(applications);

    TicketValidateRequestProcess process = new TicketValidateRequestProcess(new ServerRequestResponseProcessTest());
    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl("http://server/validateTicket?appTicket=123&signTicket=123");
    request.setQuery(new HashMap<>());
    request.query().put("appTicket", "123");
    request.query().put("signTicket", "123");

    process.process(request, response);

    Assert.assertTrue(response.getError() instanceof TicketNotFoundException);
  }

  @Test
  public void testProcessTicketValidateException() {
    LoginServerConfiguration.protocol(new LoginServerProtocolFactory());

    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);

    LoginTicketManager repository = new MemoryTicketRepository();
    LoginServerConfiguration.manager(repository);

    DefaultLoginService app = new DefaultLoginService("client", null);
    HardTimeTicketExpirePolicy policy = new HardTimeTicketExpirePolicy(10000L);
    LoginAuthorization authorization = new DefaultLoginAuthorization("user1", new HashMap<>());
    DefaultLoginTicket loginTicket = new DefaultLoginTicket("123", authorization, app, policy);
    DefaultServiceTicket appTicket = new DefaultServiceTicket("123", app, policy, loginTicket);
    repository.saveOrUpdate(appTicket);

    ServiceRepository applications = new TestApplicationRepository();
    LoginServerConfiguration.applications(applications);

    TicketValidateRequestProcess process = new TicketValidateRequestProcess(new ServerRequestResponseProcessTest());
    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl("http://server/validateTicket?appTicket=123&signTicket=123");
    request.setQuery(new HashMap<>());
    request.query().put("appTicket", "123");
    request.query().put("signTicket", "123");

    process.process(request, response);

    Assert.assertTrue(response.getError() instanceof LoginValidateException);
  }

  @Test
  public void testProcessTicketVerifySuccess() {
    LoginServerConfiguration.protocol(new LoginServerProtocolFactory());

    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);

    LoginTicketManager manager = new MemoryTicketRepository();
    LoginServerConfiguration.manager(manager);
    DefaultLoginService app = new DefaultLoginService("client", null);
    HardTimeTicketExpirePolicy policy = new HardTimeTicketExpirePolicy(10000L);
    LoginAuthorization authorization = new DefaultLoginAuthorization("user1", new HashMap<>());
    DefaultLoginTicket loginTicket = new DefaultLoginTicket("123", authorization, app, policy);
    DefaultServiceTicket appTicket = new DefaultServiceTicket("123", app, policy, loginTicket);
    manager.saveOrUpdate(appTicket);

    ServiceRepository applications = new TestApplicationRepository();
    LoginServerConfiguration.applications(applications);

    TicketValidateRequestProcess process = new TicketValidateRequestProcess(new ServerRequestResponseProcessTest());
    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl("http://server/validateTicket?appTicket=123&signTicket=123");
    request.setQuery(new HashMap<>());
    request.query().put("appTicket", "123");
    request.query().put("signTicket", RsaUtils.sign(priKey, "123"));

    process.process(request, response);

    Assert.assertTrue(response.getEntity() instanceof TicketValidateResponse);
  }

  @Test
  public void testProcessTicketVerifyException() {
    LoginServerConfiguration.protocol(new LoginServerProtocolFactory());

    LoginServerProperties properties = new LoginServerProperties();
    LoginServerConfiguration.properties(properties);

    LoginTicketManager manager = new MemoryTicketRepository();
    LoginServerConfiguration.manager(manager);

    DefaultLoginService app = new DefaultLoginService("client", null);
    HardTimeTicketExpirePolicy policy = new HardTimeTicketExpirePolicy(10000L);
    LoginAuthorization authorization = new DefaultLoginAuthorization("user1", new HashMap<>());
    DefaultLoginTicket loginTicket = new DefaultLoginTicket("123", authorization, app, policy);
    DefaultServiceTicket appTicket = new DefaultServiceTicket("123", app, policy, loginTicket);
    manager.saveOrUpdate(appTicket);

    ServiceRepository applications = new TestApplicationRepository();
    LoginServerConfiguration.applications(applications);

    TicketValidateRequestProcess process = new TicketValidateRequestProcess(new ServerRequestResponseProcessTest());
    TestServerResponse response = new TestServerResponse();
    TestServerRequest request = new TestServerRequest();
    request.setUrl("http://server/validateTicket?appTicket=123&signTicket=123");
    request.setQuery(new HashMap<>());
    request.query().put("appTicket", "123");
    request.query().put("signTicket", "123");

    process.process(request, response);

    Assert.assertTrue(response.getError() instanceof LoginValidateException);
  }
}
