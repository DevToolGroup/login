package group.devtool.component.login.client.protocol.request;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import group.devtool.component.login.client.process.ServiceTicketRequestProcess;
import group.devtool.component.login.core.exception.LoginRuntimeException;
import group.devtool.component.login.core.protocol.HttpLoginProtocolRequest;
import org.apache.http.client.methods.HttpGet;

/**
 * 默认实现，应用登录凭证验证请求
 * <p>
 * {@link ServiceTicketRequestProcess}
 */
public class DefaultServerTicketValidateRequest extends HttpGet
    implements HttpLoginProtocolRequest, ServerTicketValidateRequest {

  private static final String SIGN = "signTicket";

  private static final String TICKET = "appTicket";

  private final String serverValidateTicketUrl;

  private final String applicationTicketId;

  private final String signAppTicketId;

  /**
   * 构造应用登录凭证验证请求
   * 
   * @param serverValidateTicketUrl 登录服务验证地址
   * @param applicationTicketId     应用登录凭证ID
   * @param signAppTicketId         应用登录凭证ID签名
   */
  public DefaultServerTicketValidateRequest(String serverValidateTicketUrl, String applicationTicketId,
      String signAppTicketId) {
    super();
    this.serverValidateTicketUrl = serverValidateTicketUrl;
    this.applicationTicketId = applicationTicketId;
    this.signAppTicketId = signAppTicketId;
    setURI(requestUrl());
  }

  private URI requestUrl() {
    String requestUrl;
    try {
      requestUrl = serverValidateTicketUrl +
              "?" +
              TICKET +
              "=" +
              applicationTicketId +
              "&" +
              SIGN +
              "=" +
              URLEncoder.encode(signAppTicketId, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      throw new LoginRuntimeException("校验请求参数编码异常");
    }
    return URI.create(requestUrl);
  }

  @Override
  public String appTicketId() {
    return applicationTicketId;
  }

  @Override
  public String signAppTicketId() {
    return signAppTicketId;
  }

}
