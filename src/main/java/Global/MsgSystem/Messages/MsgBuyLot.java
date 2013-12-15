package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.Lot;
import Global.User;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgBuyLot extends MsgToDB{
    private final Lot lot;
    private final User user;
    private final long requestId;

    public MsgBuyLot(Address from, Address to, Lot lot, User user, long requestId) {
        super(from, to);
        this.lot = lot;
        this.user = user;
        this.requestId = requestId;
    }

    @Override
    void exec(DBService dbService) {
        boolean rez = dbService.buyLot(this.lot, this.user);
        dbService.getMessageSystem().sendMessage(new MsgBuyLotResult(this.getTo(),this.getFrom(),this.requestId,rez));
    }
}
