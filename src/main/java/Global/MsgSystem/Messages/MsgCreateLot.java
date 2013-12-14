package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.Ticket;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgCreateLot extends MsgToDB {
    private Ticket ticket;

    public MsgCreateLot(Address from, Address to, Ticket ticket){
        super(from, to);
        this.ticket = ticket;
    }

    @Override
    void exec(DBService dbService) {
        dbService.createLot(this.ticket);
        //TODO: answer success or not - do we need it here?

    }
}
