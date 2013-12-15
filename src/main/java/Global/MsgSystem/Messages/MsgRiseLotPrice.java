package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.Lot;
import Global.User;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgRiseLotPrice extends MsgToDB {
    private final Lot lot;
    private final int newPrice;
    private final User user;
    private final long requestId;

    public MsgRiseLotPrice(Address from, Address to,long requestId, Lot lot, User user, int newPrice){
        super(from, to);
        this.requestId = requestId;
        this.lot = lot;
        this.user = user;
        this.newPrice = newPrice;
    }

    @Override
    void exec(DBService dbService) {
        boolean rez = dbService.riseLotPrice(this.lot, this.user,this.newPrice);
        dbService.getMessageSystem().sendMessage(new MsgRiseLotPriceResult(this.getTo(),this.getFrom(),this.requestId,rez));
    }
}
