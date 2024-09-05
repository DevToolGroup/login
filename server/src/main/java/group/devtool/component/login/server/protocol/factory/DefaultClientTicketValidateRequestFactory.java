package group.devtool.component.login.server.protocol.factory;

import group.devtool.component.login.core.exception.LoginParameterIllegalException;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.protocol.LoginProtocolRequest;
import group.devtool.component.login.core.protocol.LoginProtocolRequestFactory;
import group.devtool.component.login.server.protocol.request.ClientTicketValidateRequest;
import group.devtool.component.login.server.protocol.request.impl.DefaultClientTicketValidateRequest;

import java.util.Map;

/**
 * 客户端凭证验证请求工厂的抽象类，用于创建客户端凭证验证请求实现类
 * 
 */
public class DefaultClientTicketValidateRequestFactory implements LoginProtocolRequestFactory {

  private static final String SIGN = "signTicket";
  
  private static final String TICKET = "appTicket";

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolRequest> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    Object request = args[0];
    RequestResponseProcess requestResponseProcess = (RequestResponseProcess) args[1];
    return (T) doCreate(requestResponseProcess.query(request), args);
  }

  /**
   * 创建客户端凭证验证请求实现类
   * 
   * @param queries 请求参数
   * @param args    其他参数
   * @return 凭证验证请求实现类
   */
  private ClientTicketValidateRequest doCreate(Map<String, String> queries, Object... args) {
    return new DefaultClientTicketValidateRequest(queries.get(TICKET), queries.get(SIGN));

  }

}
