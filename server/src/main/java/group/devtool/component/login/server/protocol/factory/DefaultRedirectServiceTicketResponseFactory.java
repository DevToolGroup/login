package group.devtool.component.login.server.protocol.factory;

import group.devtool.component.login.core.entity.ServiceTicket;
import group.devtool.component.login.core.exception.LoginParameterIllegalException;
import group.devtool.component.login.core.protocol.LoginProtocolResponse;
import group.devtool.component.login.core.protocol.LoginProtocolResponseFactory;
import group.devtool.component.login.core.service.RegisterService;
import group.devtool.component.login.server.protocol.response.RedirectServiceTicketResponse;
import group.devtool.component.login.server.protocol.response.impl.DefaultRedirectServiceTicketResponse;

/**
 * 重定向应用接收凭证响应构造工厂
 * 
 */
public class DefaultRedirectServiceTicketResponseFactory implements LoginProtocolResponseFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolResponse> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    RegisterService registerApplication = (RegisterService) args[0];
    ServiceTicket appTicket = (ServiceTicket) args[1];

    return (T) doCreate(registerApplication.applicationTicketUrl(), appTicket.id(), args);
  }

  /**
   * 创建重定向应用接收凭证响应
   * 
   * @param applicationTicketUrl 登录客户端应用凭证接收URL
   * @param appTicketId          应用凭证唯一标识
   * @param args                 其他参数
   * @return 重定向应用接收凭证响应
   */
  private RedirectServiceTicketResponse doCreate(String applicationTicketUrl, String appTicketId, Object... args) {
    return new DefaultRedirectServiceTicketResponse(applicationTicketUrl, appTicketId);

  }

}
