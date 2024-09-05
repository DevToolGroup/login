package group.devtool.component.login.client.protocol.factory;

import group.devtool.component.login.client.protocol.request.DefaultServerLogoutRequest;
import group.devtool.component.login.core.exception.LoginParameterIllegalException;
import group.devtool.component.login.core.process.RequestResponseProcess;
import group.devtool.component.login.core.protocol.LoginProtocolRequest;
import group.devtool.component.login.core.protocol.LoginProtocolRequestFactory;
import group.devtool.component.login.client.protocol.request.ServerLogoutRequest;

import java.util.Map;

/**
 * 登录服务登出请求
 */
public class DefaultServerLogoutRequestFactory implements LoginProtocolRequestFactory {

  private static final String ARGUMENT_TOKEN_ID = "tokenId";

  @SuppressWarnings("unchecked")
  @Override
  public <T extends LoginProtocolRequest> T create(Object... args) {
    if (args.length < 2) {
      throw new LoginParameterIllegalException("缺少必要的参数");
    }
    Object request = args[0];
    RequestResponseProcess process = (RequestResponseProcess) args[1];
    return (T) doCreate(process.query(request), args);
  }

  /**
   * 构造登录服务登出请求
   * 
   * @param queries 请求参数
   * @param args    其他参数
   * @return 登录服务登出请求
   */
  private ServerLogoutRequest doCreate(Map<String, String> queries, Object... args) {
    return new DefaultServerLogoutRequest(queries.get(ARGUMENT_TOKEN_ID));
  }

}
