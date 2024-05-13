package group.devtool.login;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

public class ServerLogoutRequestProcessTest {

  @Test
  public void testProcessSuccess() {
    LoginClientConfiguration.protocol(new LoginClientProtocolFactory());

    LoginTokenManager repository = mock(LoginTokenManager.class);
    LoginClientConfiguration.manager(repository);
    ServerLogoutRequestProcess process = new ServerLogoutRequestProcess(new RequestResponseProcessTest());

    doNothing().when(repository).clean(anyString());

    TestRequest request = new TestRequest();
    request.setUrl("http://client.com/login/server/logout?tokenId=123");
    request.setQuery(new HashMap<>());
    request.query().put("tokenId", "123");

    TestResponse response = new TestResponse();
    process.process(request, response);

    Assert.assertTrue(response.getEntity() instanceof ServerLogoutResponse);
  }

}
