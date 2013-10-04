import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 03.10.13
 * Time: 21:22
 *
 * В данном файле собраны абстракции и инструменты для введения в проект многопоточности.
 * Логика взаимодействия потоков также как и синхронизируемые участки  прописываются
 * внутри ThreadPool.
 * Возможно, надо поместить в отдельный пакет.
 * TODO: Добавить поддержку пересылки сообщений.
 *
 */

//-------------------------------------------------------------------------------------------------

/**
 * Абстракция пула Thread-ов.
 * Контейнер над потоками, обеспечивающий их взаимодействие.
 */
public class ThreadPool {
    // Массив текущих потоков
    protected ArrayList<ExtThread> threads;
    // Последний отпущенный идентификатор.
    protected int lastID;

    // Конструирование пустого
    public ThreadPool() {
        // Инициализация полей
        threads = new ArrayList<>();
        lastID = 1;
        nowIDWriter = 1;
    }

    /**
     * Создание и запуск нового потока без курируемого объекта...
     */
    public void startNew() {
        int newID = lastID++;
        // Постановка нового потока под учет
        ExtThread newThread = new ExtThread(this, newID);
        threads.add(newThread);
        // Запуск нового потока
        threads.get(threads.size() - 1).start();
    }

    /**
     * ... то же для случая с крируемы объектом
     * @param newRun объект унаследовавший интерфейс ExtRunnable
     */
    public void startNew(ExtRunnable newRun) {
        // Определение нового идентификатора
        int newID = lastID++;

        // Создание нового потока
        ExtThread newThread = new ExtThread(newRun, this, newID);
        newRun.setThread(newThread);
        threads.add(newThread);
        // запуск
        threads.get(threads.size() - 1).start();
    }

    // Идентификатор текущего потока, который пользуется SyncWrite.
    private int nowIDWriter;

    public synchronized void syncWrite(String msg, long threadID) {
        while (threadID != nowIDWriter)
            try {

                // Вошедший поток будет ждать, пока его идентификатор и ожидаемый не сравняются
                wait();
            } catch (InterruptedException e) {
                System.out.println("SyncWrite wait() прерван.");
            }

        // Запись сообщения конкретным потока
        System.out.println(msg);

        // Зацикленность ожидания потоков
        nowIDWriter++;
        if (nowIDWriter > threads.size())
            nowIDWriter = 1;

        // Пробудить все потоки (все начнут выполнение в wait)
        notifyAll();
    }
}
