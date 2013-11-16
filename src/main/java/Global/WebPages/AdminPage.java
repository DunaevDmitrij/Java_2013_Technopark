package Global.WebPages;

import Global.WebPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 02.11.13
 */

// TODO: Артем, посмотри на изменения. Здесь также добавлен пример обработки ошибок в Runtime с отображением на странице.

public class AdminPage extends WebPageImp implements WebPage {
    private static final int ENTRY = 0;         // простой вход на сайт
    private static final int REQ_SHUTDOWN = 1;  // запрос остановки сервера
    private static final int WRONG_INPUT = 2;

    // Базовый шаблон
    private static final String TEMPLATE = "admin.tml";

    private int sleepBeforeShutdown;
    private final Map<String, Object> pageVariables;   // Контекст шаблона
    private final ArrayList<String> errors;                  // ошибки обработки POST запроса

    /**
     * Здесь заполняются значения контекста по умолчанию.
     */
    public AdminPage() {
        super();
        this.pageVariables = new HashMap<>();

        // Изначально ошибок нет
        this.errors = new ArrayList<>();
        this.pageVariables.put("errors", this.errors);
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        this.errors.clear();
        return ENTRY;
    }


    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        String timeString = request.getParameter("shutdown");
        this.errors.clear();

        if (timeString != null) {
            // Ошибка пустого ввода
            if (timeString.isEmpty()) {
                this.errors.add("Empty Input");
                return WRONG_INPUT;
            }

            try {
                this.sleepBeforeShutdown = Integer.valueOf(timeString);
            } catch (NumberFormatException e) {
                // ошибка неправильного формата
                this.errors.add("Wrong number format");
                return WRONG_INPUT;
            }

            if (this.sleepBeforeShutdown < 0) {
                // ошибка отрицательного ввода
                this.errors.add("Negative number");
                return WRONG_INPUT;
            }

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
     * @return информационное сообщение об остановке
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
    @CaseHandler(routine = WRONG_INPUT, reqType = RequestType.POST)
    public String handleEmptyInput() {
        this.pageVariables.put("errors", this.errors);
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
