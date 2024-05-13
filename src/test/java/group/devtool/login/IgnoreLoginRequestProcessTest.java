package group.devtool.login;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IgnoreLoginRequestProcessTest {

  private IgnoreLoginRequestProcess process;

  @Before
  public void initProcess() {
    process = new IgnoreLoginRequestProcess(Arrays.asList("/ignoreUrl"),
        new RequestResponseProcessTest(),
        new EmptyRequestProcessTest());

  }

  @Test
  public void testMatchTrue() {
    TestRequest request = new TestRequest();
    request.setUri("/ignoreUrl");
    request.setUrl("http://localhost/ignoreUrl?param=123");
    request.setLogoutRedirectUrl("http://localhost/ignoreUrl");

    boolean result = process.match(request);
    Assert.assertTrue(result);
  }

  @Test
  public void testMatchFalse() {
    TestRequest request1 = new TestRequest();
    request1.setUri("/loginUrl");
    request1.setUrl("http://localhost/loginUrl?param=123");
    request1.setLogoutRedirectUrl("http://localhost/loginUrl");

    boolean result1 = process.match(request1);
    Assert.assertFalse(result1);
  }

  @Test
  public void testProcess() {
    TestRequest request = new TestRequest();
    request.setUri("/ignoreUrl");
    request.setUrl("http://localhost/ignoreUrl?param=123");
    request.setLogoutRedirectUrl("http://localhost/ignoreUrl");

    TestResponse response = new TestResponse();
    try {
      process.process(request, response);
      Assert.assertEquals(response.getEntity(), "empty");
    } catch (Exception e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
    }

  }
}
