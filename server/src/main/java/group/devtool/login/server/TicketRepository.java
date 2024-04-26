package group.devtool.login.server;

import java.util.function.Supplier;

import group.devtool.login.client.Ticket;
import group.devtool.login.client.TokenRepository;

/**
 * 登录凭证持久化
 */
public interface TicketRepository extends TokenRepository {

  void saveTicket(Ticket ticket);

  void updateTicket(Ticket loginTicket);

  <T extends Ticket> T getTicket(String loginTicketId, Class<T> clazz);

  void remove(String ticketId);

  <T> T lock(String string, Supplier<T> exec);

}
