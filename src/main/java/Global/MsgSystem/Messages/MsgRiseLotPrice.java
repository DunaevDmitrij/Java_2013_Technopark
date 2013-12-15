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
    private Lot lot;
    private int newPrice;
    private User user;

    public MsgRiseLotPrice(Address from, Address to, Lot lot, User user, int newPrice){
        super(from, to);
        this.lot = lot;
        this.user = user;
        this.newPrice = newPrice;
    }

    @Override
    void exec(DBService dbService) {
        dbService.riseLotPrice(this.lot,this.user,this.newPrice);
        //TODO: answer

    }
}
