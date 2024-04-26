package group.devtool.login.client;

/**
 * 请求拦截器
 */
public interface RequestInterceptProcess {

  default <Q> boolean match(Q request) {
    return true;
  }

  public <Q, R> void process(Q request, R response);

}