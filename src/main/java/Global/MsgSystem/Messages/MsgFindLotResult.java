package Global.MsgSystem.Messages;

import Global.Address;
import Global.Lot;
import Global.MechanicAuction;

import java.util.Collection;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgFindLotResult extends MsgToMechanicAuction {
    private final Collection<Lot> singleTickets;
    private final long requestId;

    public MsgFindLotResult(Address from, Address to, long requestId, Collection<Lot> results){
        super(from, to);
        this.singleTickets = results;
        this.requestId = requestId;

    }

    @Override
    void exec(MechanicAuction mechanicSales) {
        mechanicSales.lotsFound(this.requestId, this.singleTickets);
    }
}
