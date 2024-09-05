package group.devtool.component.login.server.protocol.factory;

import group.devtool.component.login.core.exception.LoginParameterIllegalException;
import group.devtool.component.login.core.protocol.LoginProtocolResponse;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.core.service.LoginService;
import group.devtool.component.login.server.LoginServerProperties;
import group.devtool.component.login.server.protocol.response.RedirectAuthenticateResponse;
import group.devtool.component.login.server.protocol.response.impl.DefaultRedirectAuthenticateResponse;

/**
 * 重定向认证响应工厂抽象类，用于创建重定向认证响应的实现类
 *
 */
public class DefaultRedirectAuthenticateResponseFactory implements LoginProtocolResponseFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolResponse> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    LoginServerProperties properties = (LoginServerProperties) args[0];
    LoginService application = (LoginService) args[1];
    return (T) doCreate(properties, application, args);
  }

  /**
   * 创建重定向认证响应的实现类
   * 
   * @param properties  登录配置
   * @param args        其他参数
   * @param application 登录应用
   * @return 重定向认证响应
   */
  private RedirectAuthenticateResponse doCreate(LoginServerProperties properties,
                                                LoginService application,
                                                Object... args) {
    return new DefaultRedirectAuthenticateResponse(properties.authenticateUrl(), application.appId(),
        application.redirectUrl());
  }

}
