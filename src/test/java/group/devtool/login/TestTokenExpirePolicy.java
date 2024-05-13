package group.devtool.login;

public class TestTokenExpirePolicy implements TokenExpirePolicy {

  private static final long serialVersionUID = 3460814268813102866L;

  @Override
  public boolean isExpired(Trackable ticket) {
    return false;
  }

}
