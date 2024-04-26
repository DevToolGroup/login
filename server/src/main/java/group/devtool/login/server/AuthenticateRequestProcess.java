package group.devtool.login.server;

import group.devtool.login.client.ApplicationAuthorizeArgument;
import group.devtool.login.client.TokenExpirePolicy;
import group.devtool.login.client.LoginApplication;
import group.devtool.login.client.LoginException;
import group.devtool.login.client.LoginUtils;
import group.devtool.login.client.RequestInterceptProcess;
import group.devtool.login.client.RequestResponseProcess;

/**
 * 认证处理器
 */
public class AuthenticateRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private LoginServerProperties properties;

  private LoginAuthenticate authenticate;

  private IdGenerator idGenerator;

  private TokenExpirePolicy expire;

  private LoginTicketFactory factory;

  private LoginRestrictStrategy restrict;

  private TicketRepository repository;

  private ApplicationAuthorizeArgument argument;

  public AuthenticateRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginServerConfiguration.properties();
    this.authenticate = LoginServerConfiguration.authenticate();
    this.idGenerator = LoginServerConfiguration.idGenerator();
    this.expire = LoginServerConfiguration.expire();
    this.factory = LoginServerConfiguration.factory();
    this.restrict = LoginServerConfiguration.restrict();
    this.repository = LoginServerConfiguration.repository();
    this.argument = LoginServerConfiguration.argument();
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
      LoginAuthorizationResult result = authenticate.authenticate(request);
      // 解析登录应用
      LoginApplication loginApplication = argument.resolve(LoginUtils.getQuery(result.originUrl()));
      // 生成登录凭证
      LoginTicket ticket = factory.create(idGenerator.nextId(),
          result.authorization(),
          loginApplication,
          expire);
      // 执行限制策略
      restrict.restrict(ticket);
      // 登录凭证持久化
      repository.saveTicket(ticket);
      // 重定向到应用授权页面
      requestResponseProcess.redirect(response, result.originUrl(), repository.create(ticket));
    } catch (LoginException e) {
      requestResponseProcess.error(response, e);
    }
  }
}
