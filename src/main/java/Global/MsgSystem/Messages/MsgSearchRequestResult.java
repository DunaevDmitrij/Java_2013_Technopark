package Global.MsgSystem.Messages;

import Global.Address;
import Global.Imps.Frontend;
import Global.Ticket;

import java.util.Collection;

/**
 * Author: artemlobachev
 * Date: 21.12.13
 */
public class MsgSearchRequestResult extends MsgToFrontend {
    private Collection<Ticket> result;

    public MsgSearchRequestResult(Address from, Address to, Collection<Ticket> result){
        super(from, to);
        this.result = result;
    }

    @Override
    void exec(Frontend frontend) {
        //TODO: result is here. Call some method of frontend to process it.

    }
}
