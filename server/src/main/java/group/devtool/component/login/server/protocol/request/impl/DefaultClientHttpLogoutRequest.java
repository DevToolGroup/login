package group.devtool.component.login.server.protocol.request.impl;

import java.net.URI;

import group.devtool.component.login.core.protocol.HttpLoginProtocolRequest;
import group.devtool.component.login.server.protocol.request.ClientLogoutRequest;
import org.apache.http.client.methods.HttpGet;


/**
 * 基于Http协议的客户端登出请求
 */
public class DefaultClientHttpLogoutRequest extends HttpGet implements HttpLoginProtocolRequest, ClientLogoutRequest {

  private final String url;

  private final String tokenId;

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
    String requestUrl = url +
            "?tokenId=" +
            tokenId;
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
