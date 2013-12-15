package Global.MsgSystem.Messages;

import Global.Address;
import Global.MechanicAuction;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgRiseLotPriceResult extends MsgToMechanicAuction {
    private final long requestId;
    private final boolean result;

    public MsgRiseLotPriceResult(Address from, Address to, long requesyId, boolean result){
        super(from, to);
        this.requestId = requesyId;
        this.result = result;

    }

    @Override
    void exec(MechanicAuction mechanicAuction) {
        mechanicAuction.lotPriceRisen(this.requestId, this.result);
    }
}