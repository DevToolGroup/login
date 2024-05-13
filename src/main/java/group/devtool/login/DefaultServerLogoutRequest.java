package group.devtool.login;

/**
 * 默认实现，根据应用的登录标识，构建登录服务请求登出客户端参数
 * 
 * {@link ServerLogoutRequestProcess}
 */
public class DefaultServerLogoutRequest implements ServerLogoutRequest {

  private String tokenId;

  /**
   * 根据应用的登录标识，构建登录服务请求登出客户端参数
   * 
   * @param tokenId 登录标识
   */
  public DefaultServerLogoutRequest(String tokenId) {
    this.tokenId = tokenId;
  }

  @Override
  public String getTokenId() {
    return tokenId;
  }

}
