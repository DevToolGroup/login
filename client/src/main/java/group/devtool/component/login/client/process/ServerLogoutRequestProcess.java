package group.devtool.component.login.client.process;

import group.devtool.component.login.client.LoginClientConfiguration;
import group.devtool.component.login.client.LoginClientProperties;
import group.devtool.component.login.core.AntPathMatch;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.core.repository.LoginTokenRepository;
import group.devtool.component.login.core.protocol.LoginProtocolRequestFactory;
import group.devtool.component.login.client.protocol.request.ServerLogoutRequest;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.client.protocol.response.ServerLogoutResponse;

/**
 * 登出请求处理器
 */
public class ServerLogoutRequestProcess implements RequestInterceptProcess {

  private final RequestResponseProcess requestResponseProcess;

  private final LoginTokenRepository repository;

  private final LoginClientProperties properties;

  private final LoginProtocolFactory protocol;

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
    return AntPathMatch.INS.match(properties.serverLogoutUrl(), url);

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
