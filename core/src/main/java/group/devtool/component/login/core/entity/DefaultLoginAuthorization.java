package group.devtool.component.login.core.entity;

import java.util.Map;

/**
 * 认证结果默认实现
 */
public class DefaultLoginAuthorization implements LoginAuthorization {

  private static final long serialVersionUID = -8241607659576535938L;

  /**
   * 登录账号
   */
  private final String account;

  /**
   * 其他属性
   */
  private final Map<String, String> attributes;

  /**
   * 初始化认证结果
   * 
   * @param account    账号
   * @param attributes 属性
   */
  public DefaultLoginAuthorization(String account, Map<String, String> attributes) {
    this.account = account;
    this.attributes = attributes;
  }

  @Override
  public String id() {
    return account;
  }

  /**
   * @return 认证属性
   */
  public Map<String, String> getAttributes() {
    return attributes;
  }

}
