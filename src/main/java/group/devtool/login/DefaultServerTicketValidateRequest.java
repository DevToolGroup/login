package group.devtool.login;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.http.client.methods.HttpGet;

/**
 * 默认实现，应用登录凭证验证请求
 * 
 * {@link ServiceTicketRequestProcess}
 */
public class DefaultServerTicketValidateRequest extends HttpGet
    implements HttpLoginProtocolRequest, ServerTicketValidateRequest {

  private static final String SIGN = "signTicket";

  private static final String TICKET = "appTicket";

  private String serverValidateTicketUrl;

  private String applicationTicketId;

  private String signAppTicketId;

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
      requestUrl = new StringBuilder()
          .append(serverValidateTicketUrl)
          .append("?")
          .append(TICKET)
          .append("=")
          .append(applicationTicketId)
          .append("&")
          .append(SIGN)
          .append("=")
          .append(URLEncoder.encode(signAppTicketId, StandardCharsets.UTF_8.name()))
          .toString();
    } catch (UnsupportedEncodingException e) {
      throw new LoginUnknownException("校验请求参数编码异常");
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
