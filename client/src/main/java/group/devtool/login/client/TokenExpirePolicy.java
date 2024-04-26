package group.devtool.login.client;

import java.io.Serializable;

/**
 * 凭证过期策略
 */
public interface TokenExpirePolicy extends Serializable {

  boolean isExpired(TrackableToken ticket);

}
