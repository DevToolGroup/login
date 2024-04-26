package group.devtool.login.client;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;

/**
 * 拦截应用登录授权码请求处理器，校验授权码并持久化登录状态
 */
public class ApplicationTicketRequestProcess implements RequestInterceptProcess {

  private RequestResponseProcess requestResponseProcess;

  private LoginClientProperties properties;

  private TokenSerializer serializer;

  private TokenRepository repository;

  private HttpClient client;

  public ApplicationTicketRequestProcess(RequestResponseProcess requestResponseProcess) {
    this.requestResponseProcess = requestResponseProcess;
    this.properties = LoginClientConfiguration.properties();
    this.serializer = LoginClientConfiguration.serializer();
    this.repository = LoginClientConfiguration.repository();
    this.client = LoginClientConfiguration.client();
  }

  @Override
  public <Q> boolean match(Q request) {
    String url = requestResponseProcess.url(request);
    return url.startsWith(properties.appTicketUrl());
  }

  @Override
  public <Q, R> void process(Q request, R response) {
    String url = requestResponseProcess.url(request);
    String applicationTicketId = LoginUtils.resolveValidateTicket(url);

    String privateKey = properties.privateKey();
    // 构造授权码验证链接
    String validateUrl = LoginUtils.constructValidateTicketUrl(properties.validateUrl(),
        applicationTicketId,
        privateKey);

    try {
      // 验证授权码
      String body = client.execute(new HttpGet(validateUrl));
      // 解析登录实体信息
      LoginToken token = serializer.deserialize(body);
      // 登录状态持久化
      LoginCookie cookie = repository.create(token);
      // 跳转至请求页面
      requestResponseProcess.redirect(response, token.application().originUrl(), cookie);
    } catch (HttpResponseException e) {
      String message = "应用授权码验证异常，响应码： " + e.getStatusCode() + "，异常原因：" + e.getReasonPhrase();
      requestResponseProcess.error(response, new LoginValidateRequestException(message));
    } catch (Exception e) {
      requestResponseProcess.error(response, new LoginValidateRequestException(e.getMessage()));
    }
  }

}
