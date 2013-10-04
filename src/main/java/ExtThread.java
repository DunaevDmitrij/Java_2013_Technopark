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
    private long id;

    // Конструктор потока без курируемого запускаемого объекта.
    ExtThread (ThreadPool _tPool, long _id) {
        super();
        // Инициализация полей
        runObj = null;
        tPool = _tPool;
        id = _id;
    }

    // Конструктор потока с запускаемым объектом
    ExtThread(ExtRunnable _runObj, ThreadPool _tPool, long _id) {
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
// =======
    //TODO I think it will be better to enclose in try catch all in this method, than use something like delay()
// >>>>>>> d4a653b3aa569191d094e0792b14c5a5471ca7df
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
                toPool(this + " Tick " + tick);
                ++tick;
            }
            toPool(this + " завершил работу");
        }
        else
            // Делегирование выполнения
            runObj.run();
    }
// <<<<<<< HEAD

    /**
     * Обращение к синхронизированному коду, с выводом сообщения в лог.
     * @param Msg инфа для вывода
     */
    public void toPool(String Msg) {
        tPool.syncWrite(Msg, id);
    }

    // При конвертировании в строку важен идентификатор потока
    @Override
    public String toString() {
        return "Поток ID = " + id + " ";
    }
}

//-------------------------------------------------------------------------------------------------