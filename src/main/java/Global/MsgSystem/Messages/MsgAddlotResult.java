package Global.MsgSystem.Messages;

import Global.Address;
import Global.MechanicAuction;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgAddLotResult extends MsgToMechanicAuction {
    private final long requestId;
    private final boolean result;

    public MsgAddLotResult(Address from, Address to, long requesyId, boolean result){
        super(from, to);
        this.requestId = requesyId;
        this.result = result;

    }

    @Override
    void exec(MechanicAuction mechanicAuction) {
        mechanicAuction.lotAdded(this.requestId, this.result);
    }
}