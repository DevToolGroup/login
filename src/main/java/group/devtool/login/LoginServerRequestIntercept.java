package group.devtool.login;

import java.util.Arrays;
import java.util.List;

/**
 * 服务端请求拦截器 {@link LoginClientRequestIntercept}
 */
public interface LoginServerRequestIntercept extends LoginRequestIntercept {

  default List<RequestInterceptProcess> loginInterceptProcesses(RequestInterceptProcess requestProcess) {
    return Arrays.asList(
        // 业务处理
        new DispatchClientRequestProcess(
            this,
            new IgnoreLoginRequestProcess(LoginClientConfiguration.ignoreUrls(), this, requestProcess),
            // 登录凭证处理
            new ServiceTicketRequestProcess(this),
            // 未登录或过期处理
            new LoginRedirectRequestProcess(this),
            // 服务端登出请求处理
            new ServerLogoutRequestProcess(this),
            // 应用登出请求处理
            new ClientLogoutRequestProcess(this),
            // 处理登录上下文，及代理业务请求
            new ProxyRequestProcess(LoginClientConfiguration.manager(), requestProcess, this)),
        // 登录处理
        new DispatchServerRequestProcess(
            this,
            // 应用登录授权处理
            new ServiceAuthorizeRequestProcess(this),
            // 登录认证处理
            new LoginAuthenticateRequestProcess(this),
            // 应用凭证校验处理
            new TicketValidateRequestProcess(this),
            // 服务端登出处理
            new LogoutRequestProcess(this)));
  }

}
