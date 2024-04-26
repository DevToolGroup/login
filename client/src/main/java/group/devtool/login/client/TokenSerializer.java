package group.devtool.login.client;

public interface TokenSerializer {

  String serialize(LoginToken token) throws LoginTokenSerializeException;

  LoginToken deserialize(String body) throws LoginTokenSerializeException;

}
