package group.devtool.login.client;

import java.util.function.Function;

/**
 * 登录状态持久化
 */
public interface TokenRepository {

  LoginCookie create(LoginToken entity);

  LoginToken resolve(Function<String, String> cookies);

  void clean(String key);

}
