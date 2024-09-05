package group.devtool.component.login.core.repository;

import group.devtool.component.login.core.entity.LoginCookie;
import group.devtool.component.login.core.entity.LoginToken;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 登录状态持久化
 */
public interface LoginTokenRepository {

  /**
   * 根据LoginToken创建LoginCookie
   *
   * @param entity          LoginToken
   * @param cookieGenerator LoginCookie生成工厂
   * @return LoginCookie http cookie
   */
  public LoginCookie saveOrUpdate(LoginToken entity, BiFunction<String, String, LoginCookie> cookieGenerator);

  /**
   * 持久化登录状态
   *
   * @param entity 登录状态
   */
  public void saveOrUpdate(LoginToken entity);

  /**
   * 解析LoginToken
   *
   * @param cookies LoginCookie容器
   * @return LoginToken
   */
  LoginToken resolve(Function<String, String> cookies);

  /**
   * 根据LoginToken的ID，清空LoginToken
   *
   * @param id 登录状态标识
   */
  void clean(String id);

}
