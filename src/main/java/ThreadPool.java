import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: artemlobachev
 * Date: 04.10.13
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
public class ThreadPool {

    private ArrayList<Thread>  threads;

    public ThreadPool(){
        threads = new ArrayList<>();
    }

    /**
     * Создает потока с именем name и запускает его на исполнение.
     * @param obj
     * @param name
     */
    public void add(Runnable obj, String name){
        Thread thread = new Thread(obj, name);
        threads.add(thread);
        thread.start();
    }

    /*
    public Object getThreadByName(String name){
        Thread result = null;
        for(int num=0; num<threads.size(); num++){
            if (threads.get(num).getName() == name)
                result = threads.get(num);
        }

        return result;
    }
    */

}
