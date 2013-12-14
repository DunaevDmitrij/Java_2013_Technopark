package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.mechanics.SingleTicket;

import java.util.ArrayList;
import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class MsgFindTicket extends MsgToDB {
    private Map<String, String> params;
    private long requestId;

    public MsgFindTicket(Address from, Address to, Map<String, String> params, long requestId){
        super(from, to);
        this.params = params;
        this.requestId = requestId;
    }

    @Override
    public void exec(DBService abonent) {
        ArrayList<SingleTicket> singleTickets = abonent.findSingleTickets(this.params);
        abonent.getMessageSystem().sendMessage(new MsgFindTicketResult(this.getTo(),this.getFrom(),requestId,singleTickets));

    }
}
