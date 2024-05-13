package group.devtool.login;


import java.util.Map;

public class LoginAuthorizationEntityImpl {

  private String id;

  private Map<String, String> attributes;

  public String getId() {
    return id;
  }

  public Map<String, String> getAttributes() {
    return attributes;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setAttributes(Map<String, String> attributes) {
    this.attributes = attributes;
  }

}
