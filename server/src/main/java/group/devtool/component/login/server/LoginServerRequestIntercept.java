package group.devtool.component.login.server;

import group.devtool.component.login.core.LoginRequestIntercept;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.server.process.*;

import java.util.Arrays;
import java.util.List;

/**
 * 服务端请求拦截器
 */
public interface LoginServerRequestIntercept extends LoginRequestIntercept {

    default List<RequestInterceptProcess> loginInterceptProcesses(RequestInterceptProcess requestProcess) {
        return Arrays.asList(
                // 应用登录授权处理
                new ServiceAuthorizeRequestProcess(this),
                // 登录认证处理
                new LoginAuthenticateRequestProcess(this),
                // 应用凭证校验处理
                new TicketValidateRequestProcess(this),
                // 服务端登出处理
                new LogoutRequestProcess(this));
    }

}
