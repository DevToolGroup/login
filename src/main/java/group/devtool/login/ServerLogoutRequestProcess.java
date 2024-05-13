package group.devtool.login;

/**
 * 登出请求处理器
 */
public class ServerLogoutRequestProcess implements RequestInterceptProcess {

  private RequestResponseProcess requestResponseProcess;

  private LoginTokenManager repository;

  private LoginClientProperties properties;

  private LoginProtocolFactory protocol;

  /**
   * 登出请求处理器
   * 
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public ServerLogoutRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.repository = LoginClientConfiguration.manager();
    this.properties = LoginClientConfiguration.properties();
    this.protocol = LoginClientConfiguration.protocol();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.serverLogoutUrl());
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    LoginProtocolRequestFactory requestFactory = protocol.request(ServerLogoutRequest.class);
    ServerLogoutRequest logoutRequest = requestFactory.create(request, requestResponseProcess);
    repository.clean(logoutRequest.getTokenId());

    LoginProtocolResponseFactory responseFactory = protocol.response(ServerLogoutResponse.class);
    ServerLogoutResponse logoutResponse = responseFactory.create(true);
    requestResponseProcess.success(response, logoutResponse);
  }

}
