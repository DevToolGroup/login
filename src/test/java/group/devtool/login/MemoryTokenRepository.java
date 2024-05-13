package group.devtool.login;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MemoryTokenRepository implements LoginTokenManager {

  private Map<String, LoginToken> cache = new ConcurrentHashMap<>();

  @Override
  public LoginCookie saveOrUpdate(LoginToken entity, BiFunction<String, String, LoginCookie> cookieFunc) {
    cache.put(entity.id(), entity);
    return cookieFunc.apply("xc-session-id", entity.id());
  }

  @Override
  public LoginToken resolve(Function<String, String> cookies) {
    String key = cookies.apply("xc-session-id");
    if (null == key) {
      return null;
    }
    return cache.get(key);
  }

  @Override
  public void clean(String key) {
    cache.remove(key);
  }

  @Override
  public void saveOrUpdate(LoginToken entity) {
    cache.put(entity.id(), entity);
  }

}
