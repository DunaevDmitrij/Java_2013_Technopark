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
 * To change this template use File | Settings | File Templates.
 */
public class Frontend extends HttpServlet implements Runnable {

    protected static String ADDRESS_AUTH = "/auth";

    private Map<String, Long> users;
    private AtomicInteger handleCount = new AtomicInteger();

    /**
     * Инициализирует подключение к БД пользователей, или пока имитирующему Map.
     * Добавляет пользователей к Map.
     */
    public Frontend(){
        users = new HashMap<>();
        users.put("vasia", 0L);
        users.put("valera", 1L);
    }

    /**
     * Выводим количество обращений каждые 5 секунд.
     */
    @Override
    public void run() {
        try{
            while (true){
                System.out.println("HandleCount = " + handleCount.get() + " ThreadID=" + Thread.currentThread().getId());
                sleep(5000);
            }
        }
        catch (InterruptedException e){
           e.printStackTrace();
        }
    }

    /**
     * Обрабатываем GET запрос.
     * @param request запрос
     * @param response ответ
     * @throws IOException TODO написать откуда может появиться!
     * @throws ServletException TODO написать откуда может появиться!
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        response.setContentType("text/html;charset=utf-8");

        WebPage page = WebPage.createPage(request.getPathInfo());
        if (page == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else {
            String pageStr = page.handleGET(request, users);
            response.getWriter().println(pageStr);
            response.setStatus(page.getStatus());
        }
    }

    /**
     * Обрабатываем POST запрос.
     * @param request
     * @param response
     * @throws IOException TODO написать откуда может появиться!
     * @throws ServletException  TODO написать откуда может появиться!
     */
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        response.setContentType("text/html;charset=utf-8");
        WebPage page = WebPage.createPage(request.getPathInfo());
        if (page == null)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        else {
            String pageStr = page.handlePOST(request, users);
            response.getWriter().println(pageStr);
            response.setStatus(page.getStatus());
        }
    }

}
