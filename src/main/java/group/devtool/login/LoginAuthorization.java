package group.devtool.login;

import java.io.Serializable;

/**
 * 登录实体信息
 */
public interface LoginAuthorization extends Serializable {

  /**
   * @return 登录实体唯一标识
   */
  String id();

}
