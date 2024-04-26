package group.devtool.login.client;

import java.util.Arrays;
import java.util.List;

/**
 * 客户端请求拦截器
 */
public interface LoginClientRequestIntercept extends LoginRequestIntercept {

  default List<RequestInterceptProcess> loginInterceptProcesses(RequestInterceptProcess requestProcess) {
    return Arrays.asList(
        // 忽略登录状态的业务处理
        new IgnoreLoginRequestProcess(LoginClientConfiguration.ignoreUrls(), this, requestProcess),
        // 应用凭证处理
        new ApplicationTicketRequestProcess(this),
        // 登录跳转处理
        new LoginRedirectRequestProcess(this),
        // 服务端登出请求处理
        new ServerLogoutRequestProcess(this),
        // 客户端登出请求处理
        new ClientLogoutRequestProcess(this),
        // 登录状态处理
        new ProxyRequestProcess(LoginClientConfiguration.repository(), requestProcess, this)
    );
  }

}
