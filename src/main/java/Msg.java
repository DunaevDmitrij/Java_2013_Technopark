/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */

//абстрактный класс Msg
public abstract class Msg {
    private Address from;
    private Address to;

    public Msg(Address from, Address to){
        this.from = from;
        this.to = to;
    }

    protected Address getFrom(){
        return from;
    }

    protected Address getTo(){
        return to;
    }

    //функция которую будет вызывать MessageSystem, без привязки к конкретному Abonent
    abstract void exec(Abonent abonent);
}

