package group.devtool.login;

/**
 * 重定向授权响应工厂的抽象类，用于创建重定向授权响应的实现类。
 * 
 * {@link LoginAuthenticateRequestProcess}
 */
public class DefaultRedirectAuthorizeResponseFactory implements LoginProtocolResponseFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolResponse> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    LoginServerProperties properties = (LoginServerProperties) args[0];
    LoginService application = (LoginService) args[1];
    return (T) doCreate(properties.authorizeUrl(), application, args);
  }

  /**
   * 创建重定向授权响应的实现类
   * 
   * @param authorizeUrl 认证地址
   * @param args         其他参数
   * @param application  登录应用
   * @return 重定向授权响应
   */
  private RedirectAuthorizeResponse doCreate(String authorizeUrl,
      LoginService application,
      Object... args) {
    return new DefaultRedirectAuthorizeResponse(authorizeUrl, application);

  }

}
