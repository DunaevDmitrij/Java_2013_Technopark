package Global.MsgSystem.Messages;

import Global.Address;
import Global.MechanicSales;
import Global.mechanics.SingleTicket;

import java.util.Collection;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class MsgFindTicketResult extends MsgToMechanics {
    private Collection<SingleTicket> singleTickets;
    private long requestId;

    public MsgFindTicketResult(Address from, Address to, long requestId, Collection<SingleTicket> results){
        super(from, to);
        this.singleTickets = results;
        this.requestId = requestId;

    }

    @Override
    void exec(MechanicSales mechanicSales) {
        mechanicSales.ticketsFound(this.requestId, this.singleTickets);
    }
}
