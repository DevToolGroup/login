package group.devtool.login;

/**
 * 登录服务登出响应构造工厂
 */
public class DefaultServerLogoutResponseFactory implements LoginProtocolResponseFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolResponse> T create(Object... args) {
    if (args.length < 1) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    boolean result = (boolean) args[0];
    return (T) doCreate(result, args);
  }

  /**
   * 构造登录服务登出响应
   * 
   * @param result 登出结果
   * @param args   其他参数
   * @return 登出响应
   */
  private ServerLogoutResponse doCreate(boolean result, Object... args) {
    return new DefaultServerLogoutResponse(result);
  }

}