package group.devtool.component.login.client.process;

import group.devtool.component.login.client.LoginClientConfiguration;
import group.devtool.component.login.client.LoginClientProperties;
import group.devtool.component.login.core.AntPathMatch;
import group.devtool.component.login.core.entity.LoginRedirection;
import group.devtool.component.login.core.entity.LoginToken;
import group.devtool.component.login.core.protocol.LoginProtocolFactory;
import group.devtool.component.login.core.process.RequestInterceptProcess;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.repository.LoginTokenRepository;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.client.protocol.response.RedirectServerLogoutResponse;

/**
 * 客户端登出处理器，拦截客户端登出请求并转发至登录服务完成登出。
 */
public class ClientLogoutRequestProcess implements RequestInterceptProcess {

    private final RequestResponseProcess requestResponseProcess;

    private final LoginClientProperties properties;

    private final LoginTokenRepository manager;

    private final LoginRedirection redirection;

    private final LoginProtocolFactory protocol;

    /**
     * 客户端登录退出处理器
     *
     * @param requestResponseProcess 请求及响应处理器
     */
    public ClientLogoutRequestProcess(RequestResponseProcess requestResponseProcess) {
        this.requestResponseProcess = requestResponseProcess;
        this.properties = LoginClientConfiguration.properties();
        this.manager = LoginClientConfiguration.manager();
        this.redirection = LoginClientConfiguration.redirection();
        this.protocol = LoginClientConfiguration.protocol();
    }

    @Override
    public <Q> boolean match(Q request) {
        String url = requestResponseProcess.url(request);
        return AntPathMatch.INS.match(properties.clientLogoutUrl(), url);
    }

    @Override
    public <Q, R> void process(Q request, R response) {
        LoginToken token = manager.resolve(name -> requestResponseProcess.cookie(request, name));
        if (null != token) {
            manager.clean(token.id());
        }
        String logoutRedirectUrl = redirection.logoutRedirectUrl(request);
        LoginProtocolResponseFactory redirectFactory = protocol.response(RedirectServerLogoutResponse.class);
        RedirectServerLogoutResponse redirectResponse = redirectFactory.create(properties, logoutRedirectUrl);
        requestResponseProcess.redirect(response, redirectResponse);
    }

}
