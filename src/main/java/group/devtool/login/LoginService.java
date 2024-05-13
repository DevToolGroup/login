package group.devtool.login;

import java.io.Serializable;

/**
 * 请求登录的应用
 */
public interface LoginService extends Serializable {

  /**
   * 
   * @return 登录应用的appID
   */
  public String appId();

  /**
   * 
   * @return 登录应用的重定向地址
   */
  public String redirectUrl();

}
