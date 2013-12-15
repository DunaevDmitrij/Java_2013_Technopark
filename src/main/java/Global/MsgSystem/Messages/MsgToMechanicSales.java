package Global.MsgSystem.Messages;

import Global.Address;
import Global.MechanicSales;
import Global.MsgSystem.Abonent;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public abstract class MsgToMechanicSales extends Msg{
    public MsgToMechanicSales(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof MechanicSales){
            this.exec((MechanicSales) abonent);
        }
    }

    abstract void exec(MechanicSales mechanicSales);
}
