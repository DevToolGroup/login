package group.devtool.component.login.core.policy;


import group.devtool.component.login.core.TokenExpirePolicy;
import group.devtool.component.login.core.entity.TimeTrackable;
import group.devtool.component.login.core.entity.Trackable;

/**
 * 基于固定时间的过期策略
 */
public class HardTimeTicketExpirePolicy implements TokenExpirePolicy {

  private static final long serialVersionUID = -1741936460087142178L;

  /**
   * 有效时间
   */
  private final Long timeToLive;

  /**
   * 固定时间的过期策略
   *
   * @param timeToLive 有效时间
   */
  public HardTimeTicketExpirePolicy(Long timeToLive) {
    this.timeToLive = timeToLive;
  }

  @Override
  public boolean isExpired(Trackable ticket) {
    if (ticket instanceof TimeTrackable) {
      return ((TimeTrackable) ticket).createTime() + timeToLive < System.currentTimeMillis();
    }
    throw new IllegalArgumentException("参数类型不支持");
  }

  /**
   * @return 有效时间
   */
  public Long getTimeToLive() {
    return timeToLive;
  }

}
