package group.devtool.component.login.server.process;

import group.devtool.component.login.core.AntPathMatch;
import group.devtool.component.login.core.LoginRestrictStrategy;
import group.devtool.component.login.core.TokenExpirePolicy;
import group.devtool.component.login.core.entity.LoginCookie;
import group.devtool.component.login.core.entity.LoginTicket;
import group.devtool.component.login.core.exception.LoginException;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.core.LoginTicketFactory;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.repository.LoginTicketRepository;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.server.protocol.response.RedirectAuthorizeResponse;
import group.devtool.component.login.core.service.LoginAuthenticate;
import group.devtool.component.login.core.service.LoginAuthorizationResult;
import group.devtool.component.login.core.service.TicketIdGenerator;
import group.devtool.component.login.server.LoginServerConfiguration;
import group.devtool.component.login.server.LoginServerProperties;

/**
 * 认证处理器
 */
public class LoginAuthenticateRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private final LoginServerProperties properties;

  private final LoginAuthenticate authenticate;

  private final TicketIdGenerator idGenerator;

  private final TokenExpirePolicy expire;

  private final LoginTicketFactory factory;

  private final LoginRestrictStrategy restrict;

  private final LoginTicketRepository manager;

  private final LoginProtocolFactory protocol;

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
    return AntPathMatch.INS.match(properties.authenticateUrl(), url);
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

      LoginCookie cookie = manager.saveOrUpdate(ticket, (name, value)-> new LoginCookie(name, value, true));
      requestResponseProcess.redirect(response, redirectResponse, cookie);
    } catch (LoginException e) {
      requestResponseProcess.error(response, e);
    }
  }
}
