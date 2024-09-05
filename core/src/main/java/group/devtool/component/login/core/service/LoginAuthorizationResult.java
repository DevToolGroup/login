package group.devtool.component.login.core.service;

import group.devtool.component.login.core.entity.LoginAuthorization;

/**
 * 认证结果
 */
public interface LoginAuthorizationResult {

  /**
   * 认证实体
   * 
   * @return 认证实体
   */
  LoginAuthorization authorization();

  /**
   * 认证的登录应用
   * 
   * @return 登录应用
   */
  LoginService loginApplication();
}
