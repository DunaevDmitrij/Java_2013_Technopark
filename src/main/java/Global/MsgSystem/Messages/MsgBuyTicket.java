package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.Ticket;
import Global.User;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class MsgBuyTicket extends MsgToDB{
    private Ticket ticket;
    private User user;
    private long requestId;

    public MsgBuyTicket(Address from, Address to, Ticket ticket, User user, long requestId) {
        super(from, to);
        this.ticket = ticket;
        this.user = user;
        this.requestId = requestId;
    }

    @Override
    void exec(DBService dbService) {

    }
}
