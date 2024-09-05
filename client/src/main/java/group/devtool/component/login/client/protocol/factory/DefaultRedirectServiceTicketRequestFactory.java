package group.devtool.component.login.client.protocol.factory;

import group.devtool.component.login.client.protocol.request.DefaultApplicationTicketRequest;
import group.devtool.component.login.core.exception.LoginParameterIllegalException;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.protocol.LoginProtocolRequest;
import group.devtool.component.login.core.protocol.LoginProtocolRequestFactory;
import group.devtool.component.login.client.protocol.request.RedirectApplicationTicketRequest;

import java.util.Map;

/**
 * 应用登录凭证参数构造器
 */
public class DefaultRedirectServiceTicketRequestFactory implements LoginProtocolRequestFactory {

  private static final String APP_TICKET = "appTicket";

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolRequest> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    Object request = args[0];
    RequestResponseProcess process = (RequestResponseProcess) args[1];
    return (T) doCreate(getApplicationTicket(request, process), request, args);
  }

  private String getApplicationTicket(Object request, RequestResponseProcess process) {
    Map<String, String> queries = getQuery(request, process);
    return queries.get(APP_TICKET);
  }

  private Map<String, String> getQuery(Object request, RequestResponseProcess process) {
    return process.query(request);
  }

  /**
   * 构造应用登录凭证重定向请求
   * 
   * @param applicationTicket 应用登录凭证
   * @param request           实际请求
   * @param args              其他参数
   * @return 应用登录凭证重定向请求
   */
  private RedirectApplicationTicketRequest doCreate(String applicationTicket, Object request, Object... args) {
    return new DefaultApplicationTicketRequest(applicationTicket);
  }


}
