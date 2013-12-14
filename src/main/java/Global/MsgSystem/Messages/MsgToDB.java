package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.MsgSystem.Abonent;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:27
 */
//абстрактный класс,
public abstract class MsgToDB extends Msg{

    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    //проверяем что Abonent принадлежит AccountService
    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof DBService){
            //вызываем выполнение сообщения для AccountServiceImp
            this.exec((DBService) abonent);
        }
    }

    abstract void exec(DBService dbService);
}