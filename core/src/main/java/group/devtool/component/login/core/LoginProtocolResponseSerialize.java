package group.devtool.component.login.core;

import group.devtool.component.login.core.protocol.LoginProtocolResponse;

/**
 * 登录响应协议序列化
 */
public interface LoginProtocolResponseSerialize {

  /**
   * 序列化响应
   * 
   * @param <T>      响应类型
   * @param response 响应参数
   * @return 字节数组
   */
  <T extends LoginProtocolResponse> byte[] serialize(T response);

  /**
   * 反序列化响应
   * 
   * @param <T>       响应类型
   * @param byteArray 字节数组
   * @param clazz     响应实际类型
   * @return 响应
   */
  <T extends LoginProtocolResponse> T deserialize(byte[] byteArray, Class<T> clazz);

}
