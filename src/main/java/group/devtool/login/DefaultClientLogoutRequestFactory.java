package group.devtool.login;

/**
 * 登出客户端请求工厂抽象类，用于参数识别并创建具体的实现类
 * 
 * {@link DefaultServerLogoutRequestFactory}
 */
public class DefaultClientLogoutRequestFactory implements LoginProtocolRequestFactory {

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolRequest> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    String url = (String) args[0];
    String tokenId = (String) args[1];
    return (T) doCreate(url, tokenId, args);
  }

  /**
   * 创建登出客户端请求实现类
   * 
   * @param url     登出客户端请求地址
   * @param tokenId 登录状态唯一标识
   * @param args    其他参数
   * @return 请求实现类
   */
  private ClientLogoutRequest doCreate(String url, String tokenId, Object... args) {
    return new DefaultClientHttpLogoutRequest(url, tokenId);
  }

}
