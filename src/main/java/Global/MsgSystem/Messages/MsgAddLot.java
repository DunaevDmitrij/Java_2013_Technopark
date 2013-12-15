package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.Ticket;

import java.util.Date;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgAddLot extends MsgToDB{
    private final Ticket ticket;
    private final int startPrice;
    private final Date closeDate;
    private long requestId;

    public MsgAddLot(Address from, Address to, long requestId, Ticket ticket, int startPrice, Date closeDate) {
        super(from, to);
        this.requestId = requestId;
        this.ticket = ticket;
        this.startPrice = startPrice;
        this.closeDate = closeDate;
    }

    @Override
    void exec(DBService dbService) {
        boolean rez = dbService.createLot(this.ticket,this.startPrice,this.closeDate);
        dbService.getMessageSystem().sendMessage(new MsgAddLotResult(this.getTo(),this.getFrom(),this.requestId,rez));
    }
}
