package group.devtool.login;

import java.util.HashMap;

public class TestLoginTokenEntityFactory {

  public static TestLoginTokenEntity fromToken(LoginToken token) {
    if (!(token instanceof DefaultLoginToken)) {
      throw new IllegalArgumentException("无效的参数类型，仅支持LoginTokenImpl类型");
    }
    DefaultLoginToken impl = (DefaultLoginToken) token;
    TestLoginTokenEntity entity = new TestLoginTokenEntity();
    entity.setId(impl.id());
    entity.setAuthorization(convert(impl.authorization()));
    entity.setApplication(convert(impl.application()));
    entity.setExpireTime(impl.getExpireTime());
    return entity;
  }

  private static LoginApplicationEntityImpl convert(LoginService application) {
    LoginApplicationEntityImpl entity = new LoginApplicationEntityImpl();
    entity.setAppId(application.appId());
    entity.setRedirectUrl(application.redirectUrl());
    return entity;
  }

  private static LoginAuthorizationEntityImpl convert(LoginAuthorization authorization) {
    if (!(authorization instanceof DefaultLoginAuthorization)) {
      throw new IllegalArgumentException("无效的参数类型，仅支持LoginTokenImpl类型");
    }
    LoginAuthorizationEntityImpl entity = new LoginAuthorizationEntityImpl();
    DefaultLoginAuthorization impl = (DefaultLoginAuthorization) authorization;
    entity.setId(impl.id());
    entity.setAttributes(new HashMap<>());
    return entity;
  }

  public static LoginToken toToken(TestLoginTokenEntity entity) {
    DefaultLoginToken loginToken = new DefaultLoginToken(entity.getId(),
        convert(entity.getAuthorization()),
        convert(entity.getApplication()), System.currentTimeMillis() + 100000);
    return loginToken;
  }

  private static LoginService convert(LoginApplicationEntityImpl application) {
    return new DefaultLoginService(application.getAppId(), application.getRedirectUrl());
  }

  private static LoginAuthorization convert(LoginAuthorizationEntityImpl entity) {
    return new DefaultLoginAuthorization(entity.getId(), entity.getAttributes());
  }

}
