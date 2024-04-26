package group.devtool.login.client;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClient implements Closeable {

  private static final HttpClientConnectionManager defaultConnectionManager;

  static {
    PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(180,
        TimeUnit.SECONDS);
    pool.setDefaultMaxPerRoute(8);
    pool.setMaxTotal(128);
    defaultConnectionManager = pool;
  }

  private static final RequestConfig defaultRequestConfig;

  static {
    defaultRequestConfig = RequestConfig.custom()
        .setConnectTimeout(10000)
        .setSocketTimeout(10000)
        .setRedirectsEnabled(false)
        .build();
  }

  private CloseableHttpClient client;

  public HttpClient() {
    this(defaultConnectionManager, defaultRequestConfig);
  }

  public HttpClient(HttpClientConnectionManager manager, RequestConfig config) {
    client = HttpClients.custom()
        .setConnectionManager(manager)
        .setDefaultRequestConfig(config)
        .build();
  }

  public String execute(HttpUriRequest request) throws IOException, HttpResponseException {
    CloseableHttpResponse response = null;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      response = client.execute(request);
      if (response.getStatusLine().getStatusCode() != 200) {
        throw new HttpResponseException(response.getStatusLine().getStatusCode(),
            response.getStatusLine().getReasonPhrase());
      }
      response.getEntity().writeTo(outputStream);
      return outputStream.toString();
    } finally {
      if (null != response) {
        response.close();
      }
      outputStream.close();
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
