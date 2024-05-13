package group.devtool.login;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) // 按照方法名称升序执行
public class LoginProcessTest {

  public String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW5ThIA3kIBYjPcBRSmmh4web1LoJIksDbM+b0gkkchOSIqD7QDPlcRLBCM1kmGOeC003ayTWB9c89H+cEkL1uiMRVYgyogEoqSFotQhZhupiX71XatFAGTliT+JDOPhWlpuy2fggfKEeG1+xStVnxe6HM7QDD4E4sV7CDJdcRKQIDAQAB";

  public String priKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJblOEgDeQgFiM9wFFKaaHjB5vUugkiSwNsz5vSCSRyE5IioPtAM+VxEsEIzWSYY54LTTdrJNYH1zz0f5wSQvW6IxFViDKiASipIWi1CFmG6mJfvVdq0UAZOWJP4kM4+FaWm7LZ+CB8oR4bX7FK1WfF7ocztAMPgTixXsIMl1xEpAgMBAAECgYAaG1r/NKCcV27J6wuNFht0pyGpMmyGl2NoGYUAMUlqdoCetzM05kW/Zb/0GDO4oG7vptTtwUmxbEC4g9xBoTQw1REC/IL7o4RVWwWDyd2wPZVyQQiXMOCIR/CkQoMpg8WbBS0uEObumSdnZrfkh51DcdzlaHpqWD0oCfjMnasrnQJBAP46yaAu5Up+/oqJ8alkcOr+q5fjYMwxIEABSWcTki8WA5ANT9Aq6O6k/GmaOcNFBR6kvNXdKBjZPyQz36Q1h8UCQQCX8jgrXxDZK5ira956fhya49b72S0JchN3LOogErsEyQMAlwBEGaEJfn6ew+edHzBlw3HagugAs6stFv45KBYVAkEAvyKDpCKd0LxZst6zCZ+yJXsCl1cj12C31mchQJW+Ohha5Vqcvu0D4ye3fc2tz9l8V+WS81cqZkQu7JDzewmj4QJAJqRgKo+Lvl86/WV6eBZ4ed+1vej2bi+HycgvZqa8zzO7wrukQq1t6fc0hnX2Alx7N3YkD1k5XWxT/SyazLhoHQJAT/Xco6TZW1Uqh0wSV0ICHexIUAJ5iIGiECRCZ1V/MAA8zCUT/UJXbUdXe8NbjQdcy+Z+sjcKitVAwMxYjTSS3Q==";

  public String serverAuthorizeUrl = "http://server/login/authorize";

  public String serverValidateTicketUrl = "http://server/login/validate";

  public String serverLogoutUrl = "http://server/login/logout";

  public String serverAuthenticateUrl = "http://server/login/authenticate";

  private String loginContext = "http://server/login";

  @Before
  public void initConfiguration() {
    initClientConfiguration();
    initServerConfiguration();
  }

  private void initClientConfiguration() {
    LoginClientConfiguration.ignoreUrls(null);
    LoginClientProperties properties = new LoginClientProperties();
    properties.appId("client");
    properties.serverAuthorizeUrl(serverAuthorizeUrl);
    properties.serverValidateTicketUrl(serverValidateTicketUrl);
    properties.serverLogoutUrl(serverLogoutUrl);
    properties.privateKey(priKey);
    LoginClientConfiguration.properties(properties);

    LoginTokenManager repository = new MemoryTokenRepository();
    LoginClientConfiguration.manager(repository);

    DefaultHttpProtocolTransport client = new DirectHttpClient();
    LoginClientConfiguration.transport(client);

    LoginClientConfiguration.protocol(new LoginClientProtocolFactory());
  }

  private void initServerConfiguration() {
    LoginServerProperties properties = new LoginServerProperties();
    properties.appId("server");
    properties.authenticateUrl(serverAuthenticateUrl);
    properties.authorizeUrl(serverAuthorizeUrl);
    properties.validateUrl(serverValidateTicketUrl);
    properties.logoutUrl(serverLogoutUrl);
    properties.loginContext(loginContext);
    LoginServerConfiguration.properties(properties);

    ServiceRepository applications = new TestApplicationRepository();
    LoginServerConfiguration.applications(applications);

    TokenExpirePolicy expire = new HardTimeTicketExpirePolicy(10000L);
    LoginServerConfiguration.expire(expire);

    LoginTicketFactory factory = new DefaultLoginTicketFactory();
    LoginServerConfiguration.factory(factory);

    TicketIdGenerator idGenerator = new FixedIDGenerator();
    LoginServerConfiguration.idGenerator(idGenerator);

    LoginTicketManager repository = new MemoryTicketRepository();
    LoginServerConfiguration.manager(repository);

    LoginRestrictStrategy restrict = new TestLoginRestrictStrategy();
    LoginServerConfiguration.restrict(restrict);

    LogoutService logoutService = mock(LogoutService.class);
    LoginServerConfiguration.logoutService(logoutService);

    LoginServerConfiguration.protocol(new LoginServerProtocolFactory());

  }

  /**
   * 验证未登录的情况
   * 1. 请求应用服务，转发至登录服务
   * 2. 登录服务转发至认证服务
   * 3. 认证服务跳转登录服务
   * 4. 登录服务跳转应用服务
   * 
   * @throws Throwable
   */
  @Test
  public void applicationLogin() throws Throwable {
    // application login
    String ticketUrl = "http://client/appTicket";
    String logoutUrl = "http://client/logout";
    String clientUrl = "http://client/biz";
    String uri = "/biz";

    LoginAuthenticate authenticate = new TestLoginAuthenticate(clientUrl);
    LoginServerConfiguration.authenticate(authenticate);

    LoginClientProperties properties = LoginClientConfiguration.properties();
    properties.clientApplicationTicketUrl(ticketUrl);
    properties.clientLogoutUrl(logoutUrl);

    TestApplicationRepository applications = (TestApplicationRepository) LoginServerConfiguration.applications();
    applications.setLogoutUrl(logoutUrl);
    applications.setValidateTicketUrl(ticketUrl);

    LoginRedirection redirection = new TestLoginRedirectUrlManager(clientUrl, null);
    LoginClientConfiguration.redirection(redirection);

    LoginClientRequestInterceptTest clientIntercept = new LoginClientRequestInterceptTest();
    LoginServerRequestIntercept serverIntercept = new LoginServerRequestInterceptTest();

    // 1. 客户端未登录，客户端请求重定向至服务端的授权地址
    TestClientResponse clientResponse = new TestClientResponse();
    TestClientRequest clientRequest = new TestClientRequest();
    clientRequest.setUrl(clientUrl);
    clientRequest.setUri(uri);
    clientRequest.setQuery(new HashMap<>());
    clientRequest.setCookies(new HashMap<>());
    clientIntercept.intercept(clientRequest, clientResponse, new EmptyRequestProcessTest());
    Assert.assertEquals(serverAuthorizeUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        clientResponse.getRedirectUrl());

    // 2. 服务端未登录，授权请求重定向至认证服务的登录页面地址（这里，直接返回登录服务的认证地址）
    TestServerResponse serverResponse = new TestServerResponse();
    TestServerRequest serverRequest = new TestServerRequest();
    serverRequest.setUrl(clientResponse.getRedirectUrl());
    serverRequest.setCookies(new HashMap<>());
    serverRequest.setQuery(new HashMap<>());
    serverRequest.query().put("appid", "client");
    serverRequest.query().put("redirectUrl", clientUrl);

    serverIntercept.intercept(serverRequest, serverResponse, new EmptyRequestProcessTest());
    Assert.assertEquals(serverAuthenticateUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        serverResponse.getRedirectUrl());

    // 3. 直接登录，服务端认证请求通过，重定向到服务端的授权地址
    TestServerResponse serverResponse1 = new TestServerResponse();
    TestServerRequest serverRequest1 = new TestServerRequest();
    serverRequest1.setUrl(serverResponse.getRedirectUrl());
    serverRequest1.setQuery(new HashMap<>());
    serverRequest1.query().put("appid", "client");
    serverRequest1.query().put("redirectUrl", clientUrl);

    serverIntercept.intercept(serverRequest1, serverResponse1, new EmptyRequestProcessTest());
    Assert.assertEquals(serverAuthorizeUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        serverResponse1.getRedirectUrl());
    Assert.assertTrue(serverResponse1.getCookies().length > 0 && serverResponse1.cookie("x-session-id") != null);

    // 4. 服务端授权，重定向到客户端的接收地址
    LoginCookie cookie = serverResponse1.cookie("x-session-id");
    TestServerResponse serverResponse2 = new TestServerResponse();
    TestServerRequest serverRequest2 = new TestServerRequest();
    serverRequest2.setUrl(serverResponse1.getRedirectUrl());
    serverRequest2.setCookies(new HashMap<>());
    serverRequest2.getCookies().put(cookie.getName(), cookie.getValue());
    serverRequest2.setQuery(new HashMap<>());
    serverRequest2.query().put("appid", "client");
    serverRequest2.query().put("redirectUrl", clientUrl);

    serverIntercept.intercept(serverRequest2, serverResponse2, new EmptyRequestProcessTest());
    Assert.assertEquals(LoginClientConfiguration.properties().clientApplicationTicketUrl() + "?appTicket=client",
        serverResponse2.getRedirectUrl());

    // 5. 客户端凭证接收并验证
    TestClientResponse clientResponse2 = new TestClientResponse();
    TestClientRequest clientRequest2 = new TestClientRequest();
    clientRequest2.setUrl(serverResponse2.getRedirectUrl());
    clientRequest2.setQuery(new HashMap<>());
    clientRequest2.query().put("appTicket", "client");

    TestServerResponse serverResponse3 = new TestServerResponse();
    TestServerRequest serverRequest3 = new TestServerRequest();
    serverRequest3.setQuery(new HashMap<>());
    serverRequest3.query().put("appTicket", "client");
    serverRequest3.query().put("signTicket", RsaUtils.sign(priKey, "client"));

    DirectHttpClient client = (DirectHttpClient) LoginClientConfiguration.transport();
    client.setRequestIntercept(serverIntercept, serverRequest3, serverResponse3);
    clientIntercept.intercept(clientRequest2, clientResponse2, new EmptyRequestProcessTest());
    Assert.assertEquals(clientUrl, clientResponse2.getRedirectUrl());
    Assert.assertTrue(null != clientResponse2.getCookies() && clientResponse2.getCookies().length > 0);
    Assert.assertTrue(null != clientResponse2.cookie("xc-session-id"));

    // 6. 访问客户端接口
    TestClientResponse clientResponse3 = new TestClientResponse();
    TestClientRequest clientRequest3 = new TestClientRequest();
    clientRequest3.setUrl(clientResponse2.getRedirectUrl());
    Map<String, String> result = new HashMap<>();
    result.put("xc-session-id", clientResponse2.cookie("xc-session-id").getValue());
    clientRequest3.setCookies(result);
    clientIntercept.intercept(clientRequest3, clientResponse3, new EmptyRequestProcessTest());
    Assert.assertEquals("empty", clientResponse3.getEntity());
  }

  @Test
  public void serverLogin() throws Throwable {
    String ticketUrl = "http://server/appTicket";
    String logoutUrl = "http://server/logout";
    String clientUrl = "http://server/biz";
    String uri = "/biz";

    LoginAuthenticate authenticate = new TestLoginAuthenticate(clientUrl);
    LoginServerConfiguration.authenticate(authenticate);

    LoginClientProperties properties = LoginClientConfiguration.properties();
    properties.clientApplicationTicketUrl(ticketUrl);
    properties.clientLogoutUrl(logoutUrl);

    TestApplicationRepository applications = (TestApplicationRepository) LoginServerConfiguration.applications();
    applications.setLogoutUrl(logoutUrl);
    applications.setValidateTicketUrl(ticketUrl);

    LoginRedirection construct = new TestLoginRedirectUrlManager(clientUrl, null);
    LoginClientConfiguration.redirection(construct);

    LoginClientRequestInterceptTest clientIntercept = new LoginClientRequestInterceptTest();
    LoginServerRequestIntercept serverIntercept = new LoginServerRequestInterceptTest();

    // 1. 客户端未登录，客户端请求重定向至服务端的授权地址
    TestClientResponse clientResponse = new TestClientResponse();
    TestClientRequest clientRequest = new TestClientRequest();
    clientRequest.setUrl(clientUrl);
    clientRequest.setUri(uri);
    clientRequest.setQuery(new HashMap<>());
    clientRequest.setCookies(new HashMap<>());
    clientIntercept.intercept(clientRequest, clientResponse, new EmptyRequestProcessTest());
    Assert.assertEquals(serverAuthorizeUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        clientResponse.getRedirectUrl());

    // 2. 服务端未登录，授权请求重定向至认证服务的登录页面地址（这里，直接返回登录服务的认证地址）
    TestServerResponse serverResponse = new TestServerResponse();
    TestServerRequest serverRequest = new TestServerRequest();
    serverRequest.setUrl(clientResponse.getRedirectUrl());
    serverRequest.setQuery(new HashMap<>());
    serverRequest.query().put("appid", "client");
    serverRequest.query().put("redirectUrl", clientUrl);
    serverRequest.setCookies(new HashMap<>());

    serverIntercept.intercept(serverRequest, serverResponse, new EmptyRequestProcessTest());
    Assert.assertEquals(serverAuthenticateUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        serverResponse.getRedirectUrl());

    // 3. 直接登录，服务端认证请求通过，重定向到服务端的授权地址
    TestServerResponse serverResponse1 = new TestServerResponse();
    TestServerRequest serverRequest1 = new TestServerRequest();
    serverRequest1.setUrl(serverResponse.getRedirectUrl());
    serverRequest1.setQuery(new HashMap<>());
    serverRequest1.query().put("appid", "client");
    serverRequest1.query().put("redirectUrl", clientUrl);
    serverRequest1.setCookies(new HashMap<>());

    serverIntercept.intercept(serverRequest1, serverResponse1, new EmptyRequestProcessTest());
    Assert.assertEquals(serverAuthorizeUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        serverResponse1.getRedirectUrl());
    Assert.assertTrue(serverResponse1.getCookies().length > 0 && serverResponse1.cookie("x-session-id") != null);

    // 4. 服务端授权，重定向到客户端的接收地址
    LoginCookie cookie = serverResponse1.cookie("x-session-id");
    TestServerResponse serverResponse2 = new TestServerResponse();
    TestServerRequest serverRequest2 = new TestServerRequest();
    serverRequest2.setUrl(serverResponse1.getRedirectUrl());
    serverRequest2.setCookies(new HashMap<>());
    serverRequest2.getCookies().put(cookie.getName(), cookie.getValue());
    serverRequest2.setQuery(new HashMap<>());
    serverRequest2.query().put("appid", "client");
    serverRequest2.query().put("redirectUrl", clientUrl);

    serverIntercept.intercept(serverRequest2, serverResponse2, new EmptyRequestProcessTest());
    Assert.assertEquals(LoginClientConfiguration.properties().clientApplicationTicketUrl() + "?appTicket=client",
        serverResponse2.getRedirectUrl());

    // 5. 客户端凭证接收并验证
    TestClientResponse clientResponse2 = new TestClientResponse();
    TestClientRequest clientRequest2 = new TestClientRequest();
    clientRequest2.setUrl(serverResponse2.getRedirectUrl());
    clientRequest2.setQuery(new HashMap<>());
    clientRequest2.query().put("appTicket", "client");
    clientRequest2.setCookies(new HashMap<>());

    TestServerResponse serverResponse3 = new TestServerResponse();
    TestServerRequest serverRequest3 = new TestServerRequest();
    serverRequest3.setQuery(new HashMap<>());
    serverRequest3.query().put("appTicket", "client");
    serverRequest3.query().put("signTicket", RsaUtils.sign(priKey, "client"));
    serverRequest3.setCookies(new HashMap<>());

    DirectHttpClient client = (DirectHttpClient) LoginClientConfiguration.transport();
    client.setRequestIntercept(serverIntercept, serverRequest3, serverResponse3);
    clientIntercept.intercept(clientRequest2, clientResponse2, new EmptyRequestProcessTest());
    Assert.assertEquals(clientUrl, clientResponse2.getRedirectUrl());
    Assert.assertTrue(null != clientResponse2.getCookies() && clientResponse2.getCookies().length > 0);
    Assert.assertTrue(null != clientResponse2.cookie("xc-session-id"));

    // 6. 访问客户端接口
    TestClientResponse clientResponse3 = new TestClientResponse();
    TestClientRequest clientRequest3 = new TestClientRequest();
    clientRequest3.setUrl(clientResponse2.getRedirectUrl());
    Map<String, String> result = new HashMap<>();
    result.put("xc-session-id", clientResponse2.cookie("xc-session-id").getValue());
    clientRequest3.setCookies(result);
    clientIntercept.intercept(clientRequest3, clientResponse3, new EmptyRequestProcessTest());
    Assert.assertEquals("empty", clientResponse3.getEntity());
  }

  @Test
  public void testLogout() throws Throwable {
    String ticketUrl = "http://client/appTicket";
    String logoutUrl = "http://client/logout";
    String clientUrl = "http://client/biz";

    String uri = "/biz";
    LoginClientProperties properties = LoginClientConfiguration.properties();
    properties.clientApplicationTicketUrl(ticketUrl);
    properties.clientLogoutUrl(logoutUrl);

    TestApplicationRepository applications = (TestApplicationRepository) LoginServerConfiguration.applications();
    applications.setLogoutUrl(logoutUrl);
    applications.setValidateTicketUrl(ticketUrl);

    LoginAuthenticate authenticate = new TestLoginAuthenticate(clientUrl);
    LoginServerConfiguration.authenticate(authenticate);

    // login
    // application login
    LoginRedirection construct = new TestLoginRedirectUrlManager(clientUrl, clientUrl);
    LoginClientConfiguration.redirection(construct);

    LoginClientRequestInterceptTest clientIntercept = new LoginClientRequestInterceptTest();
    LoginServerRequestIntercept serverIntercept = new LoginServerRequestInterceptTest();

    // 1. 客户端未登录，客户端请求重定向至服务端的授权地址
    TestClientResponse clientResponse = new TestClientResponse();
    TestClientRequest clientRequest = new TestClientRequest();
    clientRequest.setUrl(clientUrl);
    clientRequest.setUri(uri);
    clientRequest.setQuery(new HashMap<>());
    clientRequest.setCookies(new HashMap<>());

    clientIntercept.intercept(clientRequest, clientResponse, new EmptyRequestProcessTest());
    Assert.assertEquals(serverAuthorizeUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        clientResponse.getRedirectUrl());

    // 2. 服务端未登录，授权请求重定向至认证服务的登录页面地址（这里，直接返回登录服务的认证地址）
    TestServerResponse serverResponse = new TestServerResponse();
    TestServerRequest serverRequest = new TestServerRequest();
    serverRequest.setUrl(clientResponse.getRedirectUrl());
    serverRequest.setQuery(new HashMap<>());
    serverRequest.query().put("appid", "client");
    serverRequest.query().put("redirectUrl", clientUrl);
    serverRequest.setCookies(new HashMap<>());

    serverIntercept.intercept(serverRequest, serverResponse, new EmptyRequestProcessTest());
    Assert.assertEquals(serverAuthenticateUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        serverResponse.getRedirectUrl());

    // 3. 直接登录，服务端认证请求通过，重定向到服务端的授权地址
    TestServerResponse serverResponse1 = new TestServerResponse();
    TestServerRequest serverRequest1 = new TestServerRequest();
    serverRequest1.setUrl(serverResponse.getRedirectUrl());
    serverRequest1.setQuery(new HashMap<>());
    serverRequest1.query().put("appid", "client");
    serverRequest1.query().put("redirectUrl", clientUrl);
    serverRequest1.setCookies(new HashMap<>());

    serverIntercept.intercept(serverRequest1, serverResponse1, new EmptyRequestProcessTest());
    Assert.assertEquals(serverAuthorizeUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        serverResponse1.getRedirectUrl());
    Assert.assertTrue(serverResponse1.getCookies().length > 0 && serverResponse1.cookie("x-session-id") != null);

    // 4. 服务端授权，重定向到客户端的接收地址
    LoginCookie cookie = serverResponse1.cookie("x-session-id");
    TestServerResponse serverResponse2 = new TestServerResponse();
    TestServerRequest serverRequest2 = new TestServerRequest();
    serverRequest2.setUrl(serverResponse1.getRedirectUrl());
    serverRequest2.setCookies(new HashMap<>());
    serverRequest2.getCookies().put(cookie.getName(), cookie.getValue());
    serverRequest2.setQuery(new HashMap<>());
    serverRequest2.query().put("appid", "client");
    serverRequest2.query().put("redirectUrl", clientUrl);

    serverIntercept.intercept(serverRequest2, serverResponse2, new EmptyRequestProcessTest());
    Assert.assertEquals(LoginClientConfiguration.properties().clientApplicationTicketUrl() + "?appTicket=client",
        serverResponse2.getRedirectUrl());

    // 5. 客户端凭证接收并验证
    TestClientResponse clientResponse2 = new TestClientResponse();
    TestClientRequest clientRequest2 = new TestClientRequest();
    clientRequest2.setUrl(serverResponse2.getRedirectUrl());
    clientRequest2.setQuery(new HashMap<>());
    clientRequest2.query().put("appTicket", "client");
    serverRequest2.setCookies(new HashMap<>());

    TestServerResponse serverResponse3 = new TestServerResponse();
    TestServerRequest serverRequest3 = new TestServerRequest();
    serverRequest3.setQuery(new HashMap<>());
    serverRequest3.query().put("appTicket", "client");
    serverRequest3.query().put("signTicket", RsaUtils.sign(priKey, "client"));
    serverRequest3.setCookies(new HashMap<>());

    DirectHttpClient client = (DirectHttpClient) LoginClientConfiguration.transport();
    client.setRequestIntercept(serverIntercept, serverRequest3, serverResponse3);
    clientIntercept.intercept(clientRequest2, clientResponse2, new EmptyRequestProcessTest());
    Assert.assertEquals(clientUrl, clientResponse2.getRedirectUrl());
    Assert.assertTrue(null != clientResponse2.getCookies() && clientResponse2.getCookies().length > 0);
    Assert.assertTrue(null != clientResponse2.cookie("xc-session-id"));

    // 6. 访问客户端接口
    TestClientResponse clientResponse3 = new TestClientResponse();
    TestClientRequest clientRequest3 = new TestClientRequest();
    clientRequest3.setUrl(clientResponse2.getRedirectUrl());
    Map<String, String> result = new HashMap<>();
    result.put("xc-session-id", clientResponse2.cookie("xc-session-id").getValue());
    clientRequest3.setCookies(result);
    clientIntercept.intercept(clientRequest3, clientResponse3, new EmptyRequestProcessTest());
    Assert.assertEquals("empty", clientResponse3.getEntity());

    // application logout
    TestClientResponse clientLogoutResponse = new TestClientResponse();
    TestClientRequest clientLogoutRequest = new TestClientRequest();

    clientLogoutRequest.setUrl(logoutUrl);
    clientLogoutRequest.setCookies(result);
    clientLogoutRequest.setUri("/logout");

    clientIntercept.intercept(clientLogoutRequest, clientLogoutResponse, new EmptyRequestProcessTest());
    Assert.assertEquals(serverLogoutUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        clientLogoutResponse.getRedirectUrl());

    TestServerResponse serverLogoutResponse = new TestServerResponse();
    TestServerRequest serverLogoutRequest = new TestServerRequest();
    serverLogoutRequest.setUrl(clientLogoutResponse.getRedirectUrl());
    serverLogoutRequest.setQuery(new HashMap<>());
    serverLogoutRequest.query().put("appid", "client");
    serverLogoutRequest.query().put("redirectUrl", clientUrl);
    serverLogoutRequest.setCookies(new HashMap<>());
    serverLogoutRequest.getCookies().put(cookie.getName(), cookie.getValue());

    LogoutService service = LoginServerConfiguration.logoutService();
    doNothing().when(service).logout(any());

    serverIntercept.intercept(serverLogoutRequest, serverLogoutResponse, new EmptyRequestProcessTest());
    Assert.assertEquals(
        serverAuthenticateUrl + "?appid=client&redirectUrl=" + encode(clientUrl),
        serverLogoutResponse.getRedirectUrl());

  }

  private String encode(String redirectUrl) throws UnsupportedEncodingException {
    return URLEncoder.encode(redirectUrl, StandardCharsets.UTF_8.name());
  }

}
