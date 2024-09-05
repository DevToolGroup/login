package group.devtool.component.login.server.protocol.factory;

import group.devtool.component.login.core.entity.LoginToken;
import group.devtool.component.login.core.exception.LoginParameterIllegalException;
import group.devtool.component.login.core.protocol.LoginProtocolResponse;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.core.protocol.TicketValidateResponse;
import group.devtool.component.login.server.protocol.response.impl.DefaultTicketValidateResponse;

/**
 * 应用登录凭证验证响应工厂抽象类，构建应用凭证验证结果。
 * 
 */
public class DefaultTicketValidateResponseFactory implements LoginProtocolResponseFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolResponse> T create(Object... args) {
    if (args.length < 1) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    LoginToken token = (LoginToken) args[0];
    return (T) doCreate(token, args);
  }

  /**
   * 构建应用凭证验证响应
   * 
   * @param token 登录状态
   * @param args  其他参数
   * @return 应用凭证验证响应
   */
  private TicketValidateResponse doCreate(LoginToken token, Object... args) {
    return new DefaultTicketValidateResponse(token);
  }

}
