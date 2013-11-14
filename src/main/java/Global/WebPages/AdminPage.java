package Global.WebPages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 02.11.13
 */

// TODO: Артем, посмотри на изменения. Здесь также добавлен пример обработки ошибок в Runtime с отображением на странице.

public class AdminPage extends WebPageImp {
    private static final int ENTRY = 0;         // простой вход на сайт
    private static final int REQ_SHUTDOWN = 1;  // запрос остановки сервера
    private static final int EMPTY_INPUT = 2;   // пример: ошибка ввода

    // Базовый шаблон
    private static final String TEMPLATE = "admin.tml";

    private int sleepBeforeShutdown;
    private final Map<String, Object> pageVariables;   // Контекст шаблона

    /**
     * Здесь заполняются значения контекста по умолчанию.
     */
    public AdminPage() {
        super();
        this.pageVariables = new HashMap<>();
        // Изначально ошибок нет
        this.pageVariables.put("errors", new String[] {});
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        return ENTRY;
    }


    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        String timeString = request.getParameter("shutdown");

        if (timeString != null) {
            if (timeString.isEmpty()) {
                return EMPTY_INPUT;
            }

            this.sleepBeforeShutdown = Integer.valueOf(timeString);
            return REQ_SHUTDOWN;
        }
        return ENTRY;
    }

    /**
     * Простой вход на страницу администратора.
     * @return возврат шаблона с контекстом по умолчанию.
     */
    @CaseHandler(routine = ENTRY, reqType = RequestType.ANY)
    public String handleENTRY() {
        this.Status = HttpServletResponse.SC_OK;
        return generatePage(TEMPLATE, this.pageVariables);
    }


    /**
     * Запрос об остановке сервера.
     * @return информационное сообщение обостановке
     */
    @CaseHandler(routine = REQ_SHUTDOWN, reqType = RequestType.POST)
    public String handleShutdown() {
        ShutdownTask shutdownTask = new ShutdownTask(this.sleepBeforeShutdown);
        Thread shutdownTaskThread = new Thread(shutdownTask);
        shutdownTaskThread.start();
        return "Server will shut down after " + this.sleepBeforeShutdown + " ms";
    }

    /**
     * Обработчик ошибки пустого ввода.
     * @return шаблон с контекстом, у которого обновлено поле errors
     */
    @CaseHandler(routine = EMPTY_INPUT, reqType = RequestType.POST)
    public String handleEmptyInput() {
        this.pageVariables.put("errors", new String[] {"Empty Input"});
        return generatePage(TEMPLATE, this.pageVariables);
    }

    private class ShutdownTask implements Runnable {
        final int sleepTime;

        public ShutdownTask(int sleepTime) {
            super();
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();  //TODO make class Utils
            }
            System.out.append("Shutdown requested");
            System.exit(0);
        }
    }
}
