package group.devtool.login;

import org.apache.http.client.methods.HttpUriRequest;

/**
 * 基于Http协议的登录相关请求参数
 * 
 * {@link DefaultHttpProtocolTransport}
 * 
 */
public interface HttpLoginProtocolRequest extends LoginProtocolRequest, HttpUriRequest {

  
}
