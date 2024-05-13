package group.devtool.login;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;


/**
 * 默认异步登出服务
 */
public class DefaultAsyncLogoutService implements LogoutService {

  private ExecutorService executorService;

  private CloseableHttpClient client;

  /**
   * 初始化异步登出服务
   */
  public DefaultAsyncLogoutService() {
    executorService = new ThreadPoolExecutor(8,
        16,
        60,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<Runnable>(),
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.DiscardPolicy());

    PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(180,
        TimeUnit.SECONDS);
    pool.setDefaultMaxPerRoute(8);
    pool.setMaxTotal(128);

    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(60)
        .setSocketTimeout(60)
        .setRedirectsEnabled(false)
        .build();
    client = HttpClients.custom()
        .setConnectionManager(pool)
        .setDefaultRequestConfig(config)
        .build();
  }

  /**
   * 初始化异步登出服务
   * 
   * @param executorService 异步服务
   * @param client          Http Client
   */
  public DefaultAsyncLogoutService(ExecutorService executorService, CloseableHttpClient client) {
    this.executorService = executorService;
    this.client = client;
  }

  public void logout(ClientLogoutRequest clientLogoutRequest) {
    executorService.submit(() -> {
      doRequest(clientLogoutRequest);
    });
  }

  private void doRequest(ClientLogoutRequest clientLogoutRequest) {
    if (clientLogoutRequest instanceof HttpLoginProtocolRequest) {
      try {
        client.execute((HttpLoginProtocolRequest) clientLogoutRequest);
      } catch (IOException e) {
        // do nothing, todo with log
      }
    }
  }

}
