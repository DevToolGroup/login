package group.devtool.login;

import java.util.Map;

/**
 * 登录服务退出请求工厂抽象类，解析客户端登出请求参数，创建登录服务退出请求实现类。
 * 
 * {@link DefaultRedirectServerLogoutResponseFactory}
 * {@link LogoutRequestProcess}
 */
public class DefaultRedirectServerLogoutRequestFactory implements LoginProtocolRequestFactory {

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

  /**
   * 登录服务退出请求
   * 
   * @param args    其他参数
   * @param queries 请求参数
   * @return 登录服务退出请求
   */
  private RedirectServerLogoutRequest doCreate(Map<String, String> queries, Object... args) {
    return new DefaultRedirectServerLogoutRequest(queries.get(APPID), queries.get(REDIRECT));
  }

}
