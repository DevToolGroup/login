package group.devtool.login.client;


public interface Ticket extends TrackableToken {

  public String id();

  /**
   * 是否已过期
   * 
   * @return true：过期，false：未过期
   */
  boolean isExpired();
}
