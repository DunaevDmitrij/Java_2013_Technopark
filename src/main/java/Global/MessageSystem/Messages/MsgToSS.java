package Global.MessageSystem.Messages;
import Global.SessionService;

import Global.MessageSystem.Abonent;
import Global.MessageSystem.Address;

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

    //проверяем что Abonent принадлежит SessionService
    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof SessionService){
            //вызываем выполнение сообщения для SessionService
            this.exec((SessionService) abonent);
        }
    }

    abstract void exec(SessionService sessionService);
}
