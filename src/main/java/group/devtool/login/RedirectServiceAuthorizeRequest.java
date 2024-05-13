package group.devtool.login;

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
