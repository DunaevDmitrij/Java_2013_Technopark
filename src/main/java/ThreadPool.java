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
     * @param obj Runnable объект
     * @param name Имя потока
     */
    public void add(Runnable obj, String name){
        Thread thread = new Thread(obj, name);
        threads.add(thread);
        thread.start();
    }

    /**
     * Возвращает первый поток с заданным именем.
     * @param name Имя потока.
     * @return Поток с заданным именем.
     */
    public Thread getThreadByName(String name){
        Thread result = null;
        for(int num = 0, threadsCount = threads.size(); num<threadsCount; num++ ){
            if (threads.get(num).getName() == name){
                result = threads.get(num);
            }
        }

        return result;
    }

    public void interruptThread(String name){
        getThreadByName(name).interrupt();
    }

    public boolean isThreadAlive(String name){
        return getThreadByName(name).isAlive();
    }

}
