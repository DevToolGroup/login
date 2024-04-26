package group.devtool.login.client;

/**
 * 应用授权动态参数构造器
 */
public interface ApplicationAuthorizeArgument {

  /**
   * 构造包含当前访问地址, 应用AppId的 url query string
   * 
   * @param url 应用当前访问URL
   * @param appId 应用APPID
   * @return 应用登录授权的请求参数
   */
  String construct(String url, String appId);

  /**
   * 解析url query string返回登录应用信息
   * 
   * @param query url query string
   * @return 登录应用信息，返回结果不能为空
   * @throws InvalidLoginAuthorizeParameterException
   */
  LoginApplication resolve(String query) throws InvalidLoginAuthorizeParameterException;
}
