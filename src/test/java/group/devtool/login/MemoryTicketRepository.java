package group.devtool.login;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MemoryTicketRepository implements LoginTicketManager {

  private Map<String, LoginTicket> cache = new ConcurrentHashMap<>();

  private Map<String, ServiceTicket> appTicketCache = new ConcurrentHashMap<>();

  @Override
  public LoginCookie saveOrUpdate(LoginToken entity, BiFunction<String, String, LoginCookie> cookieFunc) {
    cache.put("TC" + entity.id(), (LoginTicket) entity);
    return cookieFunc.apply("x-session-id", entity.id());
  }

  @Override
  public LoginToken resolve(Function<String, String> cookies) {
    return cache.get("TC" + cookies.apply("x-session-id"));
  }

  @Override
  public void clean(String id) {
    cache.remove("TC" + id);
  }

  @Override
  public LoginTicket getTicket(String loginTicketId) {
    return cache.get("TC" + loginTicketId);
  }

  @Override
  public <T> T synchronize(LoginSupplierFunction<String> locked, LoginSupplierFunction<T> supplier)
      throws LoginException {
    String loginTicketId = locked.get();
    synchronized (loginTicketId) {
      return supplier.get();
    }
  }

  @Override
  public void saveOrUpdate(LoginToken entity) {
    cache.put("TC" + entity.id(), (LoginTicket) entity);
  }

  @Override
  public ServiceTicket getAppTicket(String ticketId) {
    return appTicketCache.get("TP" + ticketId);
  }

  @Override
  public void saveOrUpdate(ServiceTicket appTicket) {
    appTicketCache.put("TP" + appTicket.id(), appTicket);
  }

}
