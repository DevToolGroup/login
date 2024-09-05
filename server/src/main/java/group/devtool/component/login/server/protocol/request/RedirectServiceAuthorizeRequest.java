package group.devtool.component.login.server.protocol.request;

import group.devtool.component.login.core.protocol.LoginProtocolRequest;
import group.devtool.component.login.core.service.LoginService;

/**
 * 应用授权请求
 */
public interface RedirectServiceAuthorizeRequest extends LoginProtocolRequest {

  /**
   * 授权应用信息
   * 
   * @return 授权应用信息
   */
  public LoginService service();

}
