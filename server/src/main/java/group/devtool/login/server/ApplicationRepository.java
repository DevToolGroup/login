package group.devtool.login.server;

import java.util.Collection;
import java.util.List;

/**
 * 应用持久化
 */
public interface ApplicationRepository {

  RegisterApplication getApplication(String appId);

  List<RegisterApplication> getApplication(Collection<String> values);

}
