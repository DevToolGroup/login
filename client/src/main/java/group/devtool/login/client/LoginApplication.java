package group.devtool.login.client;

import java.io.Serializable;

/**
 * 请求登录的应用
 */
public interface LoginApplication extends Serializable {

  public String appId();

  public String originUrl();

}
