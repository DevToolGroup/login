package group.devtool.login;

import java.io.Serializable;

/**
 * 凭证过期策略
 */
public interface TokenExpirePolicy extends Serializable {

  /**
   * 判断凭证是否过期
   * 
   * @param ticket 凭证
   * @return true：过期，false：未过期
   */
  boolean isExpired(Trackable ticket);

}
