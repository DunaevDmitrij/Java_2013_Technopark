package Global.MsgSystem.Messages;

import Global.Address;
import Global.MechanicSales;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class MsgBuyTicketResult extends MsgToMechanicSales {
    private final long requestId;
    private final boolean result;

    public MsgBuyTicketResult(Address from, Address to, long requesyId, boolean result){
        super(from, to);
        this.requestId = requesyId;
        this.result = result;

    }

    @Override
    void exec(MechanicSales mechanicSales) {
        mechanicSales.itemBought(this.requestId, this.result);
    }
}
