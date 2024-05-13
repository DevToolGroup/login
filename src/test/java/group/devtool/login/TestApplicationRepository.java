package group.devtool.login;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestApplicationRepository implements ServiceRepository {

  public String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCW5ThIA3kIBYjPcBRSmmh4web1LoJIksDbM+b0gkkchOSIqD7QDPlcRLBCM1kmGOeC003ayTWB9c89H+cEkL1uiMRVYgyogEoqSFotQhZhupiX71XatFAGTliT+JDOPhWlpuy2fggfKEeG1+xStVnxe6HM7QDD4E4sV7CDJdcRKQIDAQAB";

  private String validateTicketUrl;

  private String logoutUrl;

  public void setValidateTicketUrl(String validateUrl) {
    this.validateTicketUrl = validateUrl;
  }

  public void setLogoutUrl(String logoutUrl) {
    this.logoutUrl = logoutUrl;
  }

  @Override
  public RegisterService getApplication(String appId) {
    List<RegisterService> applications = getApplication(Arrays.asList(appId));
    if (null == applications || applications.size() < 1) {
      return null;
    }
    return applications.get(0);
  }

  @Override
  public List<RegisterService> getApplication(Collection<String> values) {
    return Arrays.asList(new DefaultRegisterService("client", pubKey, logoutUrl, validateTicketUrl, 10000L));
  }

}
