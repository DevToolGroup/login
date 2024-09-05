package group.devtool.component.login.core.repository;

import group.devtool.component.login.core.service.RegisterService;

import java.util.Collection;
import java.util.List;

/**
 * 应用持久化
 */
public interface ServiceRepository {

  /**
   * 根据应用appId获取注册应用
   * 
   * @param appId 应用appId
   * @return 注册应用
   */
  RegisterService getApplication(String appId);

  /**
   * 根据应用appId列表批量获取注册应用
   * 
   * @param appIds 应用appId列表
   * @return 注册应用列表
   */
  List<RegisterService> getApplication(Collection<String> appIds);

}
