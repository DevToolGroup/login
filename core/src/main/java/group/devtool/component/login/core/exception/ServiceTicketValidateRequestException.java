package group.devtool.component.login.core.exception;

/**
 * 应用登录凭证校验失败
 */
public class ServiceTicketValidateRequestException extends LoginException {

  private static final long serialVersionUID = 2552798654084901590L;

  /**
   * 应用登录凭证校验异常
   * 
   * @param message 应用登录凭证校验异常说明
   */
  public ServiceTicketValidateRequestException(String message) {
    super(message);
  }

}
