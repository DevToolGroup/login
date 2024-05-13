package group.devtool.login;

/**
 * 应用授权凭证验证处理器
 */
public class TicketValidateRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private LoginServerProperties properties;

  private LoginTicketManager manager;

  private ServiceRepository applications;

  private LoginProtocolFactory protocol;

  /**
   * 初始化凭证校验处理器
   * 
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public TicketValidateRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginServerConfiguration.properties();
    this.manager = LoginServerConfiguration.manager();
    this.applications = LoginServerConfiguration.applications();
    this.protocol = LoginServerConfiguration.protocol();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.validateUrl());
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    LoginProtocolRequestFactory factory = protocol.request(ClientTicketValidateRequest.class);
    ClientTicketValidateRequest validateRequest = factory.create(request, requestResponseProcess);
    try {
      // 登录凭证更新
      LoginToken token = manager.synchronize(() -> {
        ServiceTicket appTicket = manager.getAppTicket(validateRequest.appTicketId());
        if (null == appTicket) {
          throw new TicketNotFoundException(validateRequest.appTicketId());
        }
        if (appTicket.isExpired()) {
          throw new TicketExpiredException(appTicket.id());
        }
        return appTicket.id();
      }, () -> {
        ServiceTicket appTicket = manager.getAppTicket(validateRequest.appTicketId());
        if (appTicket.isExpired()) {
          throw new TicketExpiredException(appTicket.id());
        }
        RegisterService application = applications.getApplication(appTicket.service().appId());
        // 校验签名是否合法
        boolean result = RsaUtils.verify(application.publicKey(), appTicket.id(), validateRequest.signAppTicketId());
        if (!result) {
          throw new LoginValidateException(appTicket.id());
        }
        LoginToken loginToken = appTicket.confirm();
        manager.saveOrUpdate(appTicket.loginTicket());
        return loginToken;
      });

      LoginProtocolResponseFactory responseFactory = protocol.response(TicketValidateResponse.class);
      TicketValidateResponse validateResponse = responseFactory.create(token);
      requestResponseProcess.success(response, validateResponse);

    } catch (LoginException e) {
      requestResponseProcess.error(response, e);
      return;
    }

  }
}
