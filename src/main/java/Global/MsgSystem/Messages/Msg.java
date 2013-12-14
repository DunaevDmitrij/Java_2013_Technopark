package Global.MsgSystem.Messages;

import Global.Address;
import Global.MsgSystem.Abonent;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:16
 */
//абстрактный класс Msg
public abstract class Msg {
    private final Address from;
    private final Address to;

    public Msg(Address from, Address to) {
        super();
        this.from = from;
        this.to = to;
    }

    public Address getFrom(){
        return this.from;
    }

    public Address getTo(){
        return this.to;
    }

    //функция которую будет вызывать MsgSystem, без привязки к конкретному Abonent
    public abstract void exec(Abonent abonent);
}

