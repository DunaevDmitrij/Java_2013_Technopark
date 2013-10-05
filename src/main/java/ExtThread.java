/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 04.10.13
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */

//-------------------------------------------------------------------------------------------------
/** Расширенный интерфейс запускаемого класса.
 *   Предполагает, что в его реализации объявлено поле ссылки на ExtThread.
 *  (для управления работой потока из локального run())
 */
interface ExtRunnable extends Runnable {
    public void run();                             // Код исполнения дочернего потока
    public void setThread(ExtThread thread);       // Код для инициализации ссылки на поток
    // (при реализации просто присвоить)
}

//-------------------------------------------------------------------------------------------------
/**
 *  Абстракция расширенного потока.
 *  Настраивается под нужды проекта.
 */

class ExtThread extends Thread {
    private ThreadPool tPool;         // Ссылка на агрегирующий ThreadPool
    // ( осведомленность )
    private ExtRunnable runObj;       // Ссылка на запускаемый объект (для делегирования в run())
    private int id;

    // Конструктор потока без курируемого запускаемого объекта.
    ExtThread (ThreadPool _tPool, int _id) {
        super();
        // Инициализация полей
        runObj = null;
        tPool = _tPool;
        id = _id;
    }

    // Конструктор потока с запускаемым объектом
    ExtThread(ExtRunnable _runObj, ThreadPool _tPool, int _id) {
        super();
        runObj = _runObj;
        tPool = _tPool;
        id = _id;
        // Передача ссылки на поток
        runObj.setThread(this);
    }

    /**
     *  Тело работы потока. Пишет в лог сообщения о тиках, либо делегирует
     *  выполнение курируемому Runnable объекту.
     */
    @Override
    public void run() {
        if (runObj == null)
        {
            // Если нет курируемого объекта
            int tick = 1;         // Счетчик тиков
            while (tick <= 3)
            {
                // Задержка работы
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println(this + " прерван.");
                }
                // Обращение к синхронизируемому коду.
                syncWrite(this + " Tick " + tick);
                ++tick;
            }
            syncWrite(this + " завершил работу");
        }
        else
            // Делегирование выполнения
            runObj.run();

        tPool.removeThread(id);
    }

    /**
     * Обращение к синхронизированному коду, с выводом сообщения в лог.
     * @param Msg инфа для вывода
     */
    public void syncWrite(String Msg) {
        tPool.syncWrite(Msg, id);
    }

    public long getTid() {
        return id;
    }

    // При конвертировании в строку важен идентификатор потока
    @Override
    public String toString() {
        return "Поток ID = " + id + " ";
    }
}

//-------------------------------------------------------------------------------------------------