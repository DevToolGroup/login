package group.devtool.login;

/**
 * 凭证ID生成器
 */
public interface TicketIdGenerator {

  /**
   * @return 生成凭证ID
   */
  public String nextId();

}
