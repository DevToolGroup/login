package group.devtool.login.client;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;

public class ServerLogoutRequestProcessTest {

  @Test
  public void testProcessSuccess() {
    TokenRepository repository = mock(TokenRepository.class);
    LoginClientConfiguration.repository(repository);
    ServerLogoutRequestProcess process = new ServerLogoutRequestProcess(new RequestResponseProcessTest());

    doNothing().when(repository).clean(anyString());

    TestRequest request = new TestRequest();
    request.setUrl("http://client.com/login/server/logout?tokenId=123");
    TestResponse response = new TestResponse();
    process.process(request, response);

    Assert.assertTrue((Boolean)response.getEntity());
  }


  @Test
  public void testProcessException() {
    TokenRepository repository = mock(TokenRepository.class);
    LoginClientConfiguration.repository(repository);
    ServerLogoutRequestProcess process = new ServerLogoutRequestProcess(new RequestResponseProcessTest());

    doNothing().when(repository).clean(anyString());

    TestRequest request = new TestRequest();
    request.setUrl("http://client.com/login/server/logout");
    TestResponse response = new TestResponse();
    process.process(request, response);

    Assert.assertTrue(response.getError() instanceof InvalidServerLogoutParameterException);
  }
}
