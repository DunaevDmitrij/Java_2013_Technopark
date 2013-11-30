package Global.MsgSystem.Messages;

import Global.Address;
import Global.MsgSystem.Abonent;
import Global.ResourceSystem;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 30.11.13
 * Time: 17:30
 */

public abstract class MsgToResSystem extends Msg {

    public MsgToResSystem(Address from, Address to) {
        super(from, to);
    }

    //проверяем что Abonent принадлежит Frontend
    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof ResourceSystem){
            this.exec((ResourceSystem)abonent);
        }
    }

    abstract void exec(ResourceSystem frontend);
}