package group.devtool.component.login.client.process;

import group.devtool.component.login.client.LoginClientConfiguration;
import group.devtool.component.login.client.LoginClientProperties;
import group.devtool.component.login.client.protocol.response.RedirectServiceResponse;
import group.devtool.component.login.core.AntPathMatch;
import group.devtool.component.login.core.LoginProtocolTransport;
import group.devtool.component.login.core.protocol.TicketValidateResponse;
import group.devtool.component.login.core.entity.LoginCookie;
import group.devtool.component.login.core.entity.LoginToken;
import group.devtool.component.login.core.exception.LoginException;
import group.devtool.component.login.core.exception.ServiceTicketValidateRequestException;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.repository.LoginTokenRepository;
import group.devtool.component.login.core.protocol.LoginProtocolRequestFactory;
import group.devtool.component.login.client.protocol.request.RedirectApplicationTicketRequest;
import group.devtool.component.login.client.protocol.request.ServerTicketValidateRequest;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;

/**
 * 拦截应用登录凭证请求处理器，校验并持久化登录状态
 */
public class ServiceTicketRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private final LoginClientProperties properties;

  private final LoginProtocolFactory protocol;

  private final LoginTokenRepository manager;

  private final LoginProtocolTransport transport;

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
    return AntPathMatch.INS.match(properties.clientApplicationTicketUrl(), url);
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
      LoginCookie cookie = manager.saveOrUpdate(validateResponse.getToken(), (name, value) -> new LoginCookie(name, value, true));
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
