import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 03.10.13
 * Time: 21:22
 *
 * В данном файле собраны абстракции и инструменты для введения в проект многопоточности.
 * Логика взаимодействия потоков также как и синхронизируемые участки  прописываются
 * внутри ThreadPool. Расширение реализации стандартного потока - в классе ExtThread.
 * Возможно, надо поместить в отдельный пакет.
 * TODO: Добавить поддержку пересылки сообщений.
 *
 */

/** Расширенный интерфейс запускаемого класса.
*   Предполагает, что в его реализации объявлено поле ссылки на ExtThread.
*  (для управления работой потока из локального run())
*/
interface ExtRunnable extends Runnable {
    public void run();                        // Код исполнения дочернего потока
    public void setThread(ExtThread T);       // Код для инициализации ссылки на поток
                                              // (при реализации просто присвоить)
}

//-------------------------------------------------------------------------------------------------
/**
 *  Абстракция расширенного потока.
 *  Настраивается под нужды проекта.
 */
class ExtThread extends Thread {
    private int ID;                // Идентификатор потока
    private ThreadPool TP;         // Ссылка на агрегирующий ThreadPool
                                   // ( осведомленность )
    private ExtRunnable RunObj;    // Ссылка на запускаемый объект (для делегирования в run())

    // Конструктор потока без курируемого запускаемого объекта.
    ExtThread (int _ID, ThreadPool _TP) {
        super();
        // Инициализация полей
        RunObj = null;
        ID = _ID;
        TP = _TP;
    }

    // Конструктор потока с запускаемым объектом
    ExtThread(ExtRunnable _RunObj, int _ID, ThreadPool _TP) {
        super();
        RunObj = _RunObj;
        // Передача ссылки на поток
        RunObj.setThread(this);
        ID = _ID;
        TP = _TP;
    }

    /**
     *  Тело работы потока. Пишет в лог сообщения о тиках, либо делегирует
     *  выполнение курируемому Runnable объекту.
     */
    @Override
    public void run() {
        if (RunObj == null)
        {
            // Если нет курируемого объекта
            int Tick = 1;         // Счетчик тиков
            while (Tick <= 3)
            {
                // Задержка работы
                delay(5000);
                // Обращение к синхронизируемому коду.
                toPool(this + " Tick " + Tick);
                ++Tick;
            }
            toPool(this + " завершил работу");
        }
        else
            // Делегирование выполнения
            RunObj.run();
    }

    /**
     * public оболочка над sleep
     * @param msec
     */
    public void delay(long msec) {
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(this + " прерван.");
        }
    }

    /**
     * Обращение к синхронизированному коду, с выводом сообщения в лог.
     * @param Msg инфа для вывода
     */
    public void toPool(String Msg) {
        TP.SyncWrite(Msg, ID);
    }

    // При конвертировании в строку важен идентификатор потока
    public String toString() {
        return "Поток ID = " + ID + " ";
    }
}

//-------------------------------------------------------------------------------------------------

/**
 * Абстракция пула Thread-ов.
 * Контейнер над потоками, обеспечивающий их взаимодействие.
 */
public class ThreadPool {
    // Массив текущих потоков
    protected ArrayList<ExtThread> Threads;
    // Последний отпущенный идентификатор.
    protected int LastID;

    // Конструирование пустого
    public ThreadPool() {
        // Инициализация полей
        Threads = new ArrayList<>();
        LastID = 1;
        NowIDWriter = 1;
    }

    /**
     * Создание и запуск нового потока без курируемого объекта...
     */
    public void StartNew() {
        int NewID = LastID++;
        // Постановка нового потока под учет
        ExtThread NewT = new ExtThread(NewID, this);
        Threads.add(NewT);
        // Запуск нового потока
        Threads.get(Threads.size() - 1).start();
    }

    /**
     * ... то же для случая с крируемы объектом
     * @param NewRun объект унаследовавший интерфейс ExtRunnable
     */
    public void StartNew(ExtRunnable NewRun) {
        // Определение нового идентификатора
        int NewID = LastID++;

        // Создание нового потока
        ExtThread NewT = new ExtThread(NewRun, NewID, this);
        NewRun.setThread(NewT);
        Threads.add(NewT);
        // запуск
        Threads.get(Threads.size() - 1).start();
    }

    // Идентификатор текущего потока, который пользуется SyncWrite.
    private int NowIDWriter;

    public synchronized void SyncWrite(String Msg, int TID) {
        while (TID != NowIDWriter)
            try {
                // Вошедший поток будет ждать, пока его идентификатор и ожидаемый не сравняются
                wait();
            } catch (InterruptedException e) {
                System.out.println("SyncWrite wait() прерван.");
            }

        // Запись сообщения конкретным потока
        System.out.println(Msg);

        // Зацикленность ожидания потоков
        NowIDWriter++;
        if (NowIDWriter > Threads.size())
            NowIDWriter = 1;

        // Пробудить все потоки (все начнут выполнение в wait)
        notifyAll();
    }
}
