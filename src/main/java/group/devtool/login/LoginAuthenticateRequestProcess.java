package group.devtool.login;

/**
 * 认证处理器
 */
public class LoginAuthenticateRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private LoginServerProperties properties;

  private LoginAuthenticate authenticate;

  private TicketIdGenerator idGenerator;

  private TokenExpirePolicy expire;

  private LoginTicketFactory factory;

  private LoginRestrictStrategy restrict;

  private LoginTicketManager manager;

  private LoginProtocolFactory protocol;

  /**
   * 初始化认证处理器
   * 
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public LoginAuthenticateRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginServerConfiguration.properties();
    this.authenticate = LoginServerConfiguration.authenticate();
    this.idGenerator = LoginServerConfiguration.idGenerator();
    this.expire = LoginServerConfiguration.expire();
    this.factory = LoginServerConfiguration.factory();
    this.restrict = LoginServerConfiguration.restrict();
    this.manager = LoginServerConfiguration.manager();
    this.protocol = LoginServerConfiguration.protocol();
  }

  /**
   * 判断是否是认证请求
   * 
   * @param request 请求
   */
  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.authenticateUrl());
  }

  /**
   * 调用认证接口，完成登录认证，并持久化登录状态
   * 
   * @param request  请求
   * @param response 响应
   */
  @Override
  public <Q, R> void process(Q request, R response) {
    try {
      // 登录认证
      LoginAuthorizationResult authorizationResult = authenticate.authenticate(request);

      // 生成登录凭证
      LoginTicket ticket = factory.create(idGenerator.nextId(),
          authorizationResult.authorization(),
          authorizationResult.loginApplication(),
          expire);

      // 执行限制策略
      restrict.restrict(ticket);

      // 重定向到应用授权页面
      LoginProtocolResponseFactory redirectFactory = protocol.response(RedirectAuthorizeResponse.class);
      RedirectAuthorizeResponse redirectResponse = redirectFactory.create(properties,
          authorizationResult.loginApplication());

      LoginCookie cookie = manager.saveOrUpdate(ticket, (name, value)-> {
        return new LoginCookie(name, value, true);
      });
      requestResponseProcess.redirect(response, redirectResponse, cookie);
    } catch (LoginException e) {
      requestResponseProcess.error(response, e);
    }
  }
}
