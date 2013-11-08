package Global;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 08.11.13
 * Time: 22:18
 */
public interface ThreadPool {
    void startThread(Runnable obj, String name);

    void interruptThread(String name);

    boolean isThreadAlive(String name);
}
