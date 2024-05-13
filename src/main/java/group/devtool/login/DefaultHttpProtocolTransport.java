package group.devtool.login;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * http请求处理器
 */
public class DefaultHttpProtocolTransport implements LoginProtocolTransport, Closeable {

  private CloseableHttpClient client;
  private LoginProtocolResponseSerialize serialize;

  /**
   * 采用默认配置的Http协议的登录信息交换器
   * 
   * @param serialize 序列化工具
   */
  public DefaultHttpProtocolTransport(LoginProtocolResponseSerialize serialize) {
    client = HttpClients.createDefault();
    this.serialize = serialize;
  }

  /**
   * 采用自定义配置的Http协议的登录信息交换器
   * 
   * @param manager   连接管理
   * @param config    请求配置
   * @param serialize 反序列化返回结果
   */
  public DefaultHttpProtocolTransport(HttpClientConnectionManager manager, RequestConfig config,
      LoginProtocolResponseSerialize serialize) {
    client = HttpClients.custom()
        .setConnectionManager(manager)
        .setDefaultRequestConfig(config)
        .build();
    this.serialize = serialize;
  }

  /**
   * 采用自定义HttpClient的Http协议的登录信息交换器
   * 
   * @param client    自定义HttpClient
   * @param serialize 反序列化返回结果
   */
  public DefaultHttpProtocolTransport(CloseableHttpClient client, LoginProtocolResponseSerialize serialize) {
    this.client = client;
    this.serialize = serialize;
  }

  public <T extends LoginProtocolResponse> T execute(LoginProtocolRequest request, Class<T> clazz)
      throws LoginException {
    if (!(request instanceof HttpLoginProtocolRequest)) {
      throw new LoginParameterIllegalException("仅支持HttpLoginProtocolRequest类型的参数");
    }
    CloseableHttpResponse response = null;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      HttpUriRequest httpRequest = (HttpUriRequest) request;
      response = client.execute(httpRequest);
      if (response.getStatusLine().getStatusCode() != 200) {
        throw new HttpResponseException(response.getStatusLine().getStatusCode(),
            response.getStatusLine().getReasonPhrase());
      }
      response.getEntity().writeTo(outputStream);
      return serialize.deserialize(outputStream.toByteArray(), clazz);
    } catch (IOException e) {
      throw new LoginException("反序列化异常，异常：" + e.getMessage());
    } finally {
      try {
        if (null != response) {
          response.close();
        }
        outputStream.close();
      } catch (IOException e) {
        // do nothing
      }

    }
  }

  @Override
  public void close() {
    try {
      client.close();
    } catch (IOException e) {
      // do nothing

    }
  }

}
