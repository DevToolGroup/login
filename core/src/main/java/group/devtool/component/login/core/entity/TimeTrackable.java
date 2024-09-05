package group.devtool.component.login.core.entity;

/**
 * 可追踪创建时间的实体
 */
public class TimeTrackable implements Trackable {

  private static final long serialVersionUID = -3597995910626289418L;

  /**
   * 创建时间
   */
  private final Long createTime;

  /**
   * 可追踪创建时间的登录状态
   * 
   * @param createTime 创建时间
   */
  public TimeTrackable(Long createTime) {
    this.createTime = createTime;
  }

  /**
   * @return 创建时间
   */
  public Long createTime() {
    return createTime;
  }

}
