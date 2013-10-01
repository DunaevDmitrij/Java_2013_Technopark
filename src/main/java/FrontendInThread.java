/**
 * Created with IntelliJ IDEA.
 * User: artemlobachev
 * Date: 01.10.13
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class FrontendInThread extends Thread {

    public volatile static int handleCount = 0;
    public Frontend frontend;

    public FrontendInThread() {
        super();
        frontend = new Frontend();
    }

    @Override
    public void run() {
        //синхронизированная часть: выводим имя и отдаем блокировку объукта
        synchronized (Main.threadsMonitor){
            System.out.println("Привет из побочного потока. Имя потока:"+this.getName() + ". Id потока:"+this.getId());
            Main.threadsMonitor.notifyAll();
        }
        //асинхронная часть: спим, пока не поймаем InterruptedExseption
        while (true){
            try {
                sleep(100000);
            } catch (InterruptedException e) {
                System.out.println("Bye-bye from thread id "+this.getId());
                return;
            }
        }
    }
}
