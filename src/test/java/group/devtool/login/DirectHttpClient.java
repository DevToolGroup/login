package group.devtool.login;

import org.apache.http.client.methods.HttpGet;

public class DirectHttpClient extends DefaultHttpProtocolTransport {

  private LoginRequestIntercept intercept;

  private TestServerRequest request;

  private TestServerResponse response;

  private static LoginTokenSerializeImpl implSerialize = new LoginTokenSerializeImpl();

  public DirectHttpClient() {
    super(implSerialize);

  }

  public void setRequestIntercept(LoginRequestIntercept intercept, TestServerRequest request,
      TestServerResponse response) {
    this.intercept = intercept;
    this.request = request;
    this.response = response;
  }

  @SuppressWarnings("unchecked")
  public <T extends LoginProtocolResponse> T execute(LoginProtocolRequest httpRequest, Class<T> clazz)
      throws LoginException {
    try {
      request.setUrl(((HttpGet) httpRequest).getURI().toString());
      implSerialize.setResponse(response);
      intercept.intercept(request, response, new EmptyRequestProcessTest());
    } catch (Throwable e) {
      e.printStackTrace();
      throw new LoginException(e.getMessage());
    }
    return (T) response.getEntity();
  }

  public static class LoginTokenSerializeImpl implements LoginProtocolResponseSerialize {

    private TestServerResponse response;

    public void setResponse(TestServerResponse response) {
      this.response = response;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends LoginProtocolResponse> T deserialize(byte[] value, Class<T> clazz) {
      return (T)response.getEntity();
    }

    @Override
    public <T extends LoginProtocolResponse> byte[] serialize(T response) {
      return null;
    }
  }

}
