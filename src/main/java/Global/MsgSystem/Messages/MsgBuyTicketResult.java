package Global.MsgSystem.Messages;

import Global.Address;
import Global.MechanicSales;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class MsgBuyTicketResult extends MsgToMechanics{
    private long requesyId;
    private boolean result;

    public MsgBuyTicketResult(Address from, Address to, long requesyId, boolean result){
        super(from, to);
        this.requesyId = requesyId;
        this.result = result;

    }

    @Override
    void exec(MechanicSales mechanicSales) {
        mechanicSales.ticketBought(this.requesyId,this.result);
    }
}
