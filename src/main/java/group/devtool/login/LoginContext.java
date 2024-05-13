package group.devtool.login;

/**
 * 登录上下文
 */
public class LoginContext {

  private static final ThreadLocal<LoginAuthorization> context = new ThreadLocal<>();

  /**
   * @return 登录认证结果
   */
  public static LoginAuthorization authorization() {
    return context.get();
  }

  /**
   * 设置认证结果上下文
   * 
   * @param authorization 认证结果
   */
  public static void authorization(LoginAuthorization authorization) {
    context.set(authorization);
  }

  /**
   * 清空上下文
   */
  public static void clean() {
    context.remove();
  }

}
