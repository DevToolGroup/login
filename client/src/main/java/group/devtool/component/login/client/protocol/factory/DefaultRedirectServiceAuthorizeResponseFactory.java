package group.devtool.component.login.client.protocol.factory;

import group.devtool.component.login.client.LoginClientProperties;
import group.devtool.component.login.client.protocol.response.DefaultRedirectServiceAuthorizeResponse;
import group.devtool.component.login.core.exception.LoginParameterIllegalException;
import group.devtool.component.login.core.protocol.LoginProtocolResponse;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.client.protocol.response.RedirectServiceAuthorizeResponse;

/**
 * 重定向到登录服务的响应构造工厂
 */
public class DefaultRedirectServiceAuthorizeResponseFactory implements LoginProtocolResponseFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolResponse> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    LoginClientProperties properties = (LoginClientProperties) args[0];
    String loggedRedirectUrl = (String) args[1];
    return (T) doCreate(properties, loggedRedirectUrl, args);
  }

  /**
   * 根据配置构造应用授权响应
   * 
   * @param properties        客户端配置
   * @param loggedRedirectUrl 授权登录后的跳转地址
   * @param args              其他参数
   * @return 应用授权响应
   */
  private RedirectServiceAuthorizeResponse doCreate(LoginClientProperties properties,
                                                    String loggedRedirectUrl, Object... args) {
    return new DefaultRedirectServiceAuthorizeResponse(properties.serverAuthorizeUrl(), properties.appId(),
        loggedRedirectUrl);
  }

}
