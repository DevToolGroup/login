package group.devtool.component.login.client.process;

import group.devtool.component.login.client.LoginClientConfiguration;
import group.devtool.component.login.client.LoginClientProperties;
import group.devtool.component.login.core.entity.LoginRedirection;
import group.devtool.component.login.core.entity.LoginToken;
import group.devtool.component.login.core.exception.InvalidLoginRedirectUrlException;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.repository.LoginTokenRepository;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.client.protocol.response.RedirectServiceAuthorizeResponse;

/**
 * 请求未登录时，构造应用授权链接，并重定向至登录服务
 */
public class LoginRedirectRequestProcess implements RequestInterceptProcess {
  
  private final RequestResponseProcess requestResponseProcess;

  private final LoginRedirection redirection;

  private final LoginClientProperties properties;

  private final LoginProtocolFactory protocol;

  private final LoginTokenRepository manager;

  /**
   * 构造登录重定向处理器
   * 
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public LoginRedirectRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.redirection = LoginClientConfiguration.redirection();
    this.properties = LoginClientConfiguration.properties();
    this.protocol = LoginClientConfiguration.protocol();
    this.manager = LoginClientConfiguration.manager();
  }

  @Override
  public <Q> boolean match(Q request) {
    LoginToken token = manager.resolve(name -> requestResponseProcess.cookie(request, name));
    return null == token || token.isExpired();
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    String loggedRedirectUrl = redirection.loggedRedirectUrl(request);
    if (null == loggedRedirectUrl || loggedRedirectUrl.length() == 0) {
      requestResponseProcess.error(response, new InvalidLoginRedirectUrlException((loggedRedirectUrl)));
      return;
    }
    LoginProtocolResponseFactory redirectFactory = protocol.response(RedirectServiceAuthorizeResponse.class);
    RedirectServiceAuthorizeResponse redirectResponse = redirectFactory.create(properties, loggedRedirectUrl);
    requestResponseProcess.redirect(response, redirectResponse);
  }

}
