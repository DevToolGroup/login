package group.devtool.login.server;

import java.util.Arrays;
import java.util.List;

import group.devtool.login.client.ApplicationTicketRequestProcess;
import group.devtool.login.client.ClientLogoutRequestProcess;
import group.devtool.login.client.IgnoreLoginRequestProcess;
import group.devtool.login.client.LoginClientConfiguration;
import group.devtool.login.client.LoginRedirectRequestProcess;
import group.devtool.login.client.LoginRequestIntercept;
import group.devtool.login.client.ProxyRequestProcess;
import group.devtool.login.client.RequestInterceptProcess;

/**
 * 服务端请求拦截器
 */
public interface LoginServerRequestIntercept extends LoginRequestIntercept {

  default List<RequestInterceptProcess> loginInterceptProcesses(RequestInterceptProcess requestProcess) {
    return Arrays.asList(
        // 业务处理
        new DispatchClientRequestProcess(
            this,
            new IgnoreLoginRequestProcess(LoginClientConfiguration.ignoreUrls(), this, requestProcess),
            // 登录凭证处理
            new ApplicationTicketRequestProcess(this),
            // 未登录或过期处理
            new LoginRedirectRequestProcess(this),
            // 客户端登出请求处理
            new ClientLogoutRequestProcess(this),
            // 处理登录上下文，及代理业务请求
            new ProxyRequestProcess(LoginClientConfiguration.repository(), requestProcess, this)),
        // 登录服务处理
        new DispatchServerRequestProcess(
            this,
            // 登录认证处理
            new AuthenticateRequestProcess(this),
            // 应用凭证校验处理
            new TicketValidateRequestProcess(this),
            // 应用登录授权处理
            new ApplicationAuthorizeRequestProcess(this),
            // 服务端登出处理
            new ServerLogoutRequestProcess(this)
            )
        );
  }

}
