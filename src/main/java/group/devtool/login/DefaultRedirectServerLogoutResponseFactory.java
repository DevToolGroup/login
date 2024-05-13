package group.devtool.login;

/**
 * 重定向登录服务登出响应构造工厂
 */
public class DefaultRedirectServerLogoutResponseFactory implements LoginProtocolResponseFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolResponse> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    LoginClientProperties properties = (LoginClientProperties) args[0];
    String loggedRedirectUrl = (String) args[1];
    return (T) doCreate(properties.serverLogoutUrl(), properties.appId(), loggedRedirectUrl, args);
  }

  /**
   * 构造重定向登录服务登出响应
   * 
   * @param serverLogoutUrl   登录服务请求地址
   * @param appId             退出应用的AppID
   * @param loggedRedirectUrl 退出后应用跳转地址
   * @param args              其他参数
   * @return 重定向登录服务登出响应
   */
  private RedirectServerLogoutResponse doCreate(String serverLogoutUrl,
      String appId,
      String loggedRedirectUrl,
      Object... args) {
    return new DefaultRedirectServerLogoutResponse(serverLogoutUrl, appId, loggedRedirectUrl);
  }

}
