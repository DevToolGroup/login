package group.devtool.login;

import java.net.URI;

import org.apache.http.client.methods.HttpGet;


/**
 * 基于Http协议的客户端登出请求
 */
public class DefaultClientHttpLogoutRequest extends HttpGet implements HttpLoginProtocolRequest, ClientLogoutRequest {

  private String url;

  private String tokenId;

  /**
   * 初始化客户端登出请求
   * 
   * @param url     登出请求地址
   * @param tokenId 登录状态唯一标识
   */
  public DefaultClientHttpLogoutRequest(String url, String tokenId) {
    super();
    this.url = url;
    this.tokenId = tokenId;
    setURI(requestUrl());
  }

  private URI requestUrl() {
    String requestUrl = new StringBuilder()
        .append(url)
        .append("?tokenId=")
        .append(tokenId)
        .toString();
    return URI.create(requestUrl);
  }

  /**
   * @return 登出地址
   */
  public String getUrl() {
    return url;
  }

  /**
   * @return 登录状态标识
   */
  public String getTokenId() {
    return tokenId;
  }

}
