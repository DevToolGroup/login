package group.devtool.component.login.client.process;

import group.devtool.component.login.core.entity.LoginToken;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.repository.LoginTokenRepository;

/**
 * 代理请求处理器，设置登录状态
 */
public class ProxyRequestProcess implements RequestInterceptProcess {

  private final LoginTokenRepository manager;

  private final RequestInterceptProcess wrappedRequestProcess;

  private final RequestResponseProcess requestResponseProcess;

  /**
   * 代理请求处理器，主要用于代理业务处理，设置登录状态
   * 
   * @param manager                登录状态处理器
   * @param requestProcess         业务请求处理器
   * @param requestResponseProcess 请求及响应参数处理器
   */
  public ProxyRequestProcess(LoginTokenRepository manager,
                             RequestInterceptProcess requestProcess,
                             RequestResponseProcess requestResponseProcess) {
    this.manager = manager;
    this.wrappedRequestProcess = requestProcess;
    this.requestResponseProcess = requestResponseProcess;
  }

  @Override
  public <Q, R> void process(Q request, R response) throws Exception {
    LoginToken token = manager.resolve(name -> requestResponseProcess.cookie(request, name));
    requestResponseProcess.saveLogin(token.authorization());
    try {
      wrappedRequestProcess.process(request, response);
    } finally {
      requestResponseProcess.removeLogin(token.authorization());
    }
  }
}
