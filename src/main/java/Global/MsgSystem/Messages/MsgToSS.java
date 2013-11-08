package Global.MsgSystem.Messages;
import Global.Imps.SessionServiceImp;

import Global.MsgSystem.Abonent;
import Global.MsgSystem.Address;
import Global.SessionService;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 24.10.13
 * Time: 18:54
 */
public abstract class MsgToSS extends Msg{

    public MsgToSS(Address from, Address to) {
        super(from, to);
    }

    //проверяем что Abonent принадлежит SessionServiceImp
    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof SessionService){
            //вызываем выполнение сообщения для SessionServiceImp
            this.exec((SessionService) abonent);
        }
    }

    abstract void exec(SessionService sessionService);
}
