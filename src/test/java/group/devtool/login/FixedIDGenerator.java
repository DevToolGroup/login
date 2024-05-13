package group.devtool.login;

public class FixedIDGenerator implements TicketIdGenerator {

  @Override
  public String nextId() {
    return "client";
  }

}
