import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: artemlobachev
 * Date: 21.09.13
 * Time: 10:22
 */
public class Frontend extends HttpServlet implements Runnable {

    private final Map<String, Long> users;
    private final AtomicInteger handleCount = new AtomicInteger();

    /**
     * Инициализирует подключение к БД пользователей, или пока имитирующему Map.
     * Добавляет пользователей к Map.
     */
    public Frontend() {
        super();
        this.users = new HashMap<>();
        this.users.put("vasia", 0L);
        this.users.put("valera", 1L);
    }

    /**
     * Выводим количество обращений каждые 5 секунд.
     */
    @Override
    public void run() {
        try{
            final int fiveSec = 5*1000;
            while (true){
                System.out.println("HandleCount = " + this.handleCount.get() + " ThreadID=" + Thread.currentThread().getId());
                sleep(fiveSec);
            }
        }
        catch (InterruptedException e){
           e.printStackTrace();
        }
    }

    /**
     * Создание объекта страницы в зависимости от переданного URL.
     * @param Path строка-параметр для сопоставления
     * @return объект WebPage с нужной реализацией
     */
    public static WebPage createPage(String Path) {
        if (Path.equals("/auth") || Path.equals("/test")) {
            return new AuthPage();
        } else {
            return null;
        }
    }

    /**
     * Обрабатываем GET запрос.
     * @param request запрос
     * @param response ответ
     * @throws IOException TODO написать откуда может появиться!
     * @throws ServletException TODO написать откуда может появиться!
     */
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        // TODO: Пока предполагается, что всегда возвращаем html.
        // TODO: Если иначе, то перенести в WebPage
        response.setContentType("text/html;charset=utf-8");

        // Создание объекта страницы, в зависимости от запрашиваемого URL
        WebPage page = createPage(request.getPathInfo());
        if (page == null) {
            // Обработка неизвестного URL
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            // Получение страницы строкой. Выполняет анализ сессии, выборку контента и генерацию страницы.
            String pageStr = page.handleGET(request, this.users);
            response.getWriter().println(pageStr);
            // Установка статуса после выполнения handleGET
            response.setStatus(page.getStatus());
        }

        this.handleCount.getAndIncrement();
    }

    /**
     * Обрабатываем POST запрос.
     * @param request объект запроса
     * @param response объект ответа сервера
     * @throws IOException TODO написать откуда может появиться!
     * @throws ServletException  TODO написать откуда может появиться!
     */
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");

        // Создание объекта страницы
        WebPage page = createPage(request.getPathInfo());
        if (page == null) {
            // Обработка неизвестного URL
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            // Генерация страницы
            String pageStr = page.handlePOST(request, this.users);
            response.getWriter().println(pageStr);
            // Установка статуса
            response.setStatus(page.getStatus());
        }

        this.handleCount.getAndIncrement();
    }

}
