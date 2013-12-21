package Global.MsgSystem.Messages;

import Global.Address;
import Global.MechanicSales;
import Global.Ticket;

import java.util.Collection;
import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 21.12.13
 */
public class MsgSearchRequest extends MsgToMechanicSales {
    private Map<String,String> params;

    public MsgSearchRequest(Address from, Address to, Map<String,String> params){
        super(from, to);
        this.params = params;
    }

    @Override
    void exec(MechanicSales mechanicSales) {
        Collection<Ticket> rez = mechanicSales.search(this.params);
        mechanicSales.getMessageSystem().sendMessage(new MsgSearchRequestResult(this.getTo(), this.getFrom(),rez));
    }
}
