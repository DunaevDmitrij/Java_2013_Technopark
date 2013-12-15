package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.Lot;

import java.util.ArrayList;
import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgFindLot extends MsgToDB {
    private final Map<String, String> params;
    private final long requestId;

    public MsgFindLot(Address from, Address to, Map<String, String> params, long requestId){
        super(from, to);
        this.params = params;
        this.requestId = requestId;
    }

    @Override
    public void exec(DBService abonent) {
        ArrayList<Lot> singleTickets = abonent.findLots(this.params);
        abonent.getMessageSystem().sendMessage(new MsgFindLotResult(this.getTo(),this.getFrom(), this.requestId,singleTickets));

    }
}

