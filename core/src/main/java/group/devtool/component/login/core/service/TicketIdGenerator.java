package group.devtool.component.login.core.service;

/**
 * 凭证ID生成器
 */
public interface TicketIdGenerator {

  /**
   * @return 生成凭证ID
   */
  String nextId();

}
