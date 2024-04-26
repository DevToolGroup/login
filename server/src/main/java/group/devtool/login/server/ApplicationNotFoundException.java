package group.devtool.login.server;

import group.devtool.login.client.LoginException;

/**
 * 应用不存在
 */
public class ApplicationNotFoundException extends LoginException {

  private static final long serialVersionUID = 1L;

  public ApplicationNotFoundException(String message) {
    super(message);
  }

}
