/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vaidm
 * Date: 12.10.13
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public abstract class MsgToFrontend extends Msg{

    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    //проверяем что Abonent принадлежит Frontend
    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof Frontend){
            exec((Frontend)abonent);
        }
    }

    abstract void exec(Frontend frontend);
}
