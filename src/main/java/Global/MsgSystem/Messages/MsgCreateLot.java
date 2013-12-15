package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.Ticket;

import java.util.Date;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgCreateLot extends MsgToDB {
    private Ticket ticket;
    private int startPrice;
    private Date closeDate;

    public MsgCreateLot(Address from, Address to, Ticket ticket, int startPrice, Date closeDate){
        super(from, to);
        this.ticket = ticket;
        this.startPrice = startPrice;
        this.closeDate = closeDate;
    }

    @Override
    void exec(DBService dbService) {
        dbService.createLot(this.ticket, this.startPrice, this.closeDate);
        //TODO: answer success or not - do we need it here?

    }
}
