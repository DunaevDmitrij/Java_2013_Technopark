package Global.MsgSystem.Messages;

import Global.AccountService;
import Global.Imps.AccountServiceImp;
import Global.MsgSystem.Abonent;
import Global.Address;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:27
 */
//абстрактный класс,
public abstract class MsgToAS extends Msg{

    public MsgToAS(Address from, Address to) {
        super(from, to);
    }

    //проверяем что Abonent принадлежит AccountService
    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof AccountService){
            //вызываем выполнение сообщения для AccountService
            this.exec((AccountService) abonent);
        }
    }

    abstract void exec(AccountService accountService);
}