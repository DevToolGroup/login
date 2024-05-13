package group.devtool.login;

/**
 * 拦截应用登录凭证请求处理器，校验并持久化登录状态
 */
public class ServiceTicketRequestProcess implements RequestInterceptProcess {

  private RequestResponseProcess requestResponseProcess;

  private LoginClientProperties properties;

  private LoginProtocolFactory protocol;

  private LoginTokenManager manager;

  private LoginProtocolTransport transport;

  /**
   * 应用登录凭证请求处理器
   * 
   * @param requestResponseProcess 请求响应处理器
   */
  public ServiceTicketRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginClientConfiguration.properties();
    this.transport = LoginClientConfiguration.transport();
    this.manager = LoginClientConfiguration.manager();
    this.protocol = LoginClientConfiguration.protocol();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.clientApplicationTicketUrl());
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    LoginProtocolRequestFactory factory = protocol.request(RedirectApplicationTicketRequest.class);
    RedirectApplicationTicketRequest appTicketRequest = factory.create(request, requestResponseProcess);

    try {
      // 验证授权码
      // 构造授权码验证请求
      LoginProtocolRequestFactory validateFactory = protocol.request(ServerTicketValidateRequest.class);
      ServerTicketValidateRequest validateRequest = validateFactory.create(properties, appTicketRequest);

      TicketValidateResponse validateResponse = transport.execute(validateRequest, TicketValidateResponse.class);
      LoginToken token = validateResponse.getToken();
      // 登录状态持久化
      LoginCookie cookie = manager.saveOrUpdate(validateResponse.getToken(), (name, value) -> {
        return new LoginCookie(name, value, true);
      });
      // 跳转至请求页面
      LoginProtocolResponseFactory redirectFactory = protocol.response(RedirectServiceResponse.class);
      RedirectServiceResponse redirectResponse = redirectFactory.create(token);
      requestResponseProcess.redirect(response, redirectResponse, cookie);
    } catch (LoginException e) {
      String message = "应用授权码验证异常，异常： " + e.getMessage();
      requestResponseProcess.error(response, new ServiceTicketValidateRequestException(message));
    }
  }
}
