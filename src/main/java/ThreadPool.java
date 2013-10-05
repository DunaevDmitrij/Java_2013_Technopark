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
        nowWriter = 0;
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

    /**
     * Закрытие потока (вызывается самим потоком) и его исключение из-под надзора.
     * @param threadID  идентификатор потока
     */
    public synchronized void removeThread(int threadID) {
        int last = threads.size() - 1;

        // Проверка корректности идентификатора
        if (threads.get(last).getTid() >= threadID) {
            // Цикл по потокам с конца
            for (int i = last; i >= 0; --i)
                if (threads.get(i).getTid() == threadID) {
                    threads.remove(i);

                    // изменение счетчика ожидания
                    if (nowWriter > 0) nowWriter--;
                    else nowWriter = last;

                    // Сообщение
                    System.out.println("Остановлен " + threadID);
                    break;
                }
        }
    }

    // Номер в threads текущего потока, который пользуется SyncWrite.
    private int nowWriter;

    public synchronized void syncWrite(String msg, int threadID) {
        while (threadID != threads.get(nowWriter).getTid())
            try {
                // Вошедший поток будет ждать, пока его идентификатор и ожидаемый не сравняются
                wait();
            } catch (InterruptedException e) {
                System.out.println("SyncWrite wait() прерван.");
            }

        // Запись сообщения конкретным потока
        System.out.println(msg);

        // Зацикленность ожидания потоков
        nowWriter++;
        if (nowWriter >= threads.size())
            nowWriter = 0;

        // Пробудить все потоки (все начнут выполнение в wait)
        notifyAll();
    }
}
