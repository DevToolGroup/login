package group.devtool.login;

/**
 * 登录凭证持久化
 */
public interface LoginTicketManager extends LoginTokenManager {

  /**
   * 登录凭证同步操作
   * 
   * @param <T>      返回结果类型
   * @param locked   同步资源
   * @param supplier 业务操作
   * @return 具体返回结果
   * @throws LoginException 持久化异常
   */
  <T> T synchronize(LoginSupplierFunction<String> locked, LoginSupplierFunction<T> supplier) throws LoginException;

  /**
   * 查询登录凭证
   * 
   * @param ticketId 登录凭证ID
   * @return 登录凭证
   */
  LoginTicket getTicket(String ticketId);

  /**
   * 查询应用登录凭证
   * 
   * @param ticketId 应用登录凭证ID
   * @return 应用登录凭证
   */
  ServiceTicket getAppTicket(String ticketId);

  /**
   * 持久化应用登录凭证
   * 
   * @param appTicket 应用登录凭证
   */
  void saveOrUpdate(ServiceTicket appTicket);

  /**
   * 函数接口
   */
  public interface LoginSupplierFunction<T> {

    /**
     * 执行函数
     * 
     * @return 函数结果
     * @throws LoginException 执行异常
     */
    public T get() throws LoginException;

  }

}
