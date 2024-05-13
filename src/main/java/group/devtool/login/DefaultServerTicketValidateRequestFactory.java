package group.devtool.login;

/**
 * 应用登录凭证校验请求构造工厂
 * 
 */
public class DefaultServerTicketValidateRequestFactory implements LoginProtocolRequestFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolRequest> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    LoginClientProperties properties = (LoginClientProperties) args[0];
    RedirectApplicationTicketRequest appTicketRequest = (RedirectApplicationTicketRequest) args[1];
    return (T) doCreate(properties, appTicketRequest.applicationTicketId(), args);
  }

  /**
   * 构造应用登录凭证校验请求
   * 
   * @param properties          应用配置
   * @param applicationTicketId 登录凭证标识
   * @param args                其他参数
   * @return 校验请求
   */
  private ServerTicketValidateRequest doCreate(LoginClientProperties properties, String applicationTicketId,
      Object... args) {
    return new DefaultServerTicketValidateRequest(properties.serverValidateTicketUrl(),
        applicationTicketId,
        RsaUtils.sign(properties.privateKey(), applicationTicketId));
  }

}
