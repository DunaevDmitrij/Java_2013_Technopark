package Global.MsgSystem.Messages;

import Global.Address;
import Global.MechanicAuction;
import Global.MsgSystem.Abonent;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public abstract class MsgToMechanicAuction extends Msg {
    public MsgToMechanicAuction(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof MechanicAuction){
            this.exec((MechanicAuction) abonent);
        }
    }

    abstract void exec(MechanicAuction mechanicSales);
}
