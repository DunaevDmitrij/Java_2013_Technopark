package Global.MessageSystem.Messages;

import Global.MessageSystem.Abonent;
import Global.MessageSystem.Address;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vaidm
 * Date: 12.10.13
 * Time: 11:05
 */
public abstract class MsgToFrontend extends Msg {

    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    //проверяем что Abonent принадлежит Frontend
    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof Frontend){
            this.exec((Frontend)abonent);
        }
    }

    abstract void exec(Frontend frontend);
}
