package group.devtool.login.server;

import group.devtool.login.client.LoginAuthorization;

public class ServerLoginContextTest {
  
  private static final ThreadLocal<LoginAuthorization> CONTEXT = new ThreadLocal<>();

  public static LoginAuthorization get() {
    return CONTEXT.get();
  }

  public static void set(LoginAuthorization authorization) {
    CONTEXT.set(authorization);
  }

  public static void clean() {
    CONTEXT.remove();
  }
}
