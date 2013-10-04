import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 03.10.13
 * Time: 21:22
 *
 */

//TODO where is it used and why?
interface ExtRunnable extends Runnable {
    //TODO does it overrides the same in Runnable?
    public void run();
    //TODO did I understood right that it means that classes, that implement this interface, must realise passing them to any thread by them self?
    public void setThread(ExtThread T);
}

//-------------------------------------------------------------------------------------------------
//TODO what is it used for?
class ExtThread extends Thread {
    private int ID;
    private ThreadPool TP;
    private ExtRunnable RunObj;

    ExtThread (int _ID, ThreadPool _TP) {
        super();
        RunObj = null;
        ID = _ID;
        TP = _TP;
    }

    ExtThread(ExtRunnable _RunObj, int _ID, ThreadPool _TP) {
        super();
        RunObj = _RunObj;
        RunObj.setThread(this);
        ID = _ID;
        TP = _TP;
    }

    //TODO I think it will be better to enclose in try catch all in this method, than use something like delay()
    @Override
    public void run() {
        if (RunObj == null)
        {
            int Tick = 1;
            while (Tick <= 3)
            {
                delay(5000);
                toPool(this + " Tick " + Tick);
                ++Tick;
            }
            toPool(this + " завершил работу");
        }
        else
            RunObj.run();
    }
    //TODO why function has parametr, but sleep uses constant?
    public void delay(long msec) {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(this + " прерван.");
        }
    }

    public void toPool(String Msg) {
        TP.SyncWrite(Msg, ID);
    }
    //TODO use @Override
    public String toString() {
        return "Поток ID = " + ID + " ";
    }
}

//-------------------------------------------------------------------------------------------------

public class ThreadPool {
    protected ArrayList<ExtThread> Threads;
    protected int LastID;
    private int NowID;

    public ThreadPool() {
        Threads = new ArrayList<>();
        LastID = 1;
        NowID = 1;
    }

    public void StartNew() {
        int NewID = LastID++;
        ExtThread NewT = new ExtThread(NewID, this);
        Threads.add(NewT);
        Threads.get(Threads.size() - 1).start();
    }

    public void StartNew(ExtRunnable NewRun) {
        int NewID = LastID++;

        ExtThread NewT = new ExtThread(NewRun, NewID, this);
        NewRun.setThread(NewT);
        Threads.add(NewT);
        Threads.get(Threads.size() - 1).start();
    }
    //TODO WHAT IS IT? o_O
    public synchronized void SyncWrite(String Msg, int TID) {
        while (TID != NowID)
            try {
                wait(); //TODO WHAT IS IT? o_O I think, we mustn't wait here, but look over all threads
            } catch (InterruptedException e) {
                System.out.println("SyncWrite wait() прерван.");
            }

        System.out.println(Msg);

        NowID++;
        if (NowID > Threads.size())
            NowID = 1;

        notifyAll();
    }
}


//TODO comments!
//TODO different classes in different files(except included in other classes) = best practise in java
//TODO variable names are always written with small letter, classes with big