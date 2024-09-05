package group.devtool.component.login.client.protocol.factory;

import group.devtool.component.login.client.protocol.response.RedirectServiceResponse;
import group.devtool.component.login.core.entity.LoginToken;
import group.devtool.component.login.core.exception.LoginParameterIllegalException;
import group.devtool.component.login.client.protocol.response.DefaultRedirectServiceResponse;
import group.devtool.component.login.core.protocol.LoginProtocolResponse;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.core.service.LoginService;

/**
 * 重定向应用的响应构造起
 */
public class DefaultRedirectServiceResponseFactory implements LoginProtocolResponseFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolResponse> T create(Object... args) {
    if (args.length < 1) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    LoginToken token = (LoginToken) args[0];
    return (T) doCreate(token.application(), args);
  }

  /**
   * 构造跳转应用响应
   * 
   * @param application 登录应用信息
   * @param args        其他参数
   * @return 跳转响应
   */
  private RedirectServiceResponse doCreate(LoginService application, Object... args) {
    return new DefaultRedirectServiceResponse(application.redirectUrl());
  }

}
