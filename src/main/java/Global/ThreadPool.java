package Global;

import java.util.ArrayList;

/**
 * Author: artemlobachev
 * Date: 04.10.13
 * Time: 21:29
*/
@SuppressWarnings("UnusedDeclaration")
public class ThreadPool {

    private final ArrayList<Thread>  threads;

    public ThreadPool() {
        super();
        this.threads = new ArrayList<>();
    }

    /**
     * Создает потока с именем name из Runnable объекта и запускает его на исполнение.
     * @param obj Runnable объект
     * @param name Имя потока
     */
    public void startThread(Runnable obj, String name){
        Thread thread = new Thread(obj, name);
        this.threads.add(thread);
        thread.start();
    }

    @SuppressWarnings("UnusedDeclaration")//will be used in nearest feature
    public void interruptThread(String name){
        this.getThreadByName(name).interrupt();
    }


    @SuppressWarnings("UnusedDeclaration") //will be used in nearest feature
    public boolean isThreadAlive(String name){
        return this.getThreadByName(name).isAlive();
    }

    /**
     * Возвращает первый поток с заданным именем.
     * @param name Имя потока.
     * @return Поток с заданным именем.
     */
    private Thread getThreadByName(String name){
        Thread result = null;
        for (Thread thread : this.threads) {
            if (thread.getName().equals(name)) {
                result = thread;
            }
        }

        return result;
    }

}
