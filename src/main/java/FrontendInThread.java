/**
 * Created with IntelliJ IDEA.
 * User: artemlobachev
 * Date: 01.10.13
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class FrontendInThread extends Thread {
    @Override
    public void run() {
        System.out.println("Привет из побочного потока. Имя потока:"+this.getName() + ". Id потока:"+this.getId());
    }
}