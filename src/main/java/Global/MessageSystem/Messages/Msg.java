package Global.MessageSystem.Messages;

import Global.MessageSystem.Abonent;
import Global.MessageSystem.Address;

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

    protected Address getFrom(){
        return this.from;
    }

    protected Address getTo(){
        return this.to;
    }

    //функция которую будет вызывать MessageSystem, без привязки к конкретному Abonent
    abstract void exec(Abonent abonent);
}

