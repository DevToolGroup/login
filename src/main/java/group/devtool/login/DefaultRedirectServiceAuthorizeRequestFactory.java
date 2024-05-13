package group.devtool.login;

import java.util.Map;

/**
 * 
 * 重定向应用授权请求工厂的抽象类，用于创建重定向应用授权请求的具体实现类
 * 
 * {@link DefaultRedirectServiceAuthorizeResponseFactory}
 * {@link ServiceAuthorizeRequestProcess}
 */
public class DefaultRedirectServiceAuthorizeRequestFactory implements LoginProtocolRequestFactory {

  private static final String REDIRECT = "redirectUrl";

  private static final String APPID = "appid";

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

  private RedirectServiceAuthorizeRequest doCreate(Map<String, String> queries, Object... args) {
    return new DefaultRedirectServiceAuthorizeRequest(queries.get(APPID), queries.get(REDIRECT));
  }

}
