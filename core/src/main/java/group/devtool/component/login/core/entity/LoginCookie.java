package group.devtool.component.login.core.entity;

/**
 * 登录Cookie
 */
public final class LoginCookie {

  private final String name;

  private final String value;

  private final boolean httpOnly;

  /**
   * 根据参数构造登录Cookie
   * 
   * @param name     cookie名称
   * @param value    cookie值
   * @param httpOnly 是否限制httpOnly
   */
  public LoginCookie(String name, String value, boolean httpOnly) {
    this.name = name;
    this.value = value;
    this.httpOnly = httpOnly;
  }

  /**
   * @return cookie名称
   */
  public String getName() {
    return name;
  }

  /**
   * @return cookie值
   */
  public String getValue() {
    return value;
  }

  /**
   * @return 是否限制httpOnly
   */
  public boolean isHttpOnly() {
    return httpOnly;
  }

}
