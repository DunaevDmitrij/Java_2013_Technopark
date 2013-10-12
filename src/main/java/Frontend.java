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

import static java.lang.Thread.sleep;

/**
 * Author: artemlobachev
 * Date: 21.09.13
 */
public class Frontend extends HttpServlet implements Runnable {

    protected static final String ADDRESS_AUTH = "/auth";
    private final Map<String, Long> users;
    private final AtomicInteger handleCount = new AtomicInteger();
    private static final int STATISTICS_OUT_PERIOD = 5000;

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
            while (true){
                System.out.println("HandleCount = " + this.handleCount.get() + " ThreadID=" + Thread.currentThread().getId());
                sleep(STATISTICS_OUT_PERIOD);
            }
        }
        catch (InterruptedException e){
           e.printStackTrace();
        }
    }

    /**
     *
     * @return текущее время в заданном формате.
     */
    public static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss"); // здесь задаем формат времени
        return formatter.format(date);
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

        this.handleCount.getAndIncrement();
        System.out.println("Frontend thread ID=" + Thread.currentThread().getId());

        response.setContentType("text/html;charset=utf-8");

        //если пользователь пришел на страницу авторизации
        if (request.getPathInfo().equals(ADDRESS_AUTH)){

            //получаем id http сессии и userId, если его нет
            HttpSession session = request.getSession();
            Long userId = (Long) session.getAttribute("userId");

            //если пользователь не авторизован
            if (userId == null)
            {
                //отдаем страницу авторизации
                //TODO отдавать статическую страницу не запуская шаблонизатор, быстрее будет
                response.getWriter().println(PageGenerator.getPage("auth.tml", new HashMap<String, Object>()));
            }
            //пользователь авторизован
            else
            {
                //получаем из сессии имя пользователя и id сессии
                String name = (String) session.getAttribute("userName");
                String sessionId = session.getId();
                //отдаем страницу с ними
                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put("UserID", userId);
                pageVariables.put("Time", getTime());
                pageVariables.put("User", name);
                pageVariables.put("Session", sessionId);
                response.getWriter().println(PageGenerator.getPage("test.tml", pageVariables));
            }

            response.setStatus(HttpServletResponse.SC_OK);
        }
        //пользователь пришел на необрабатываемый адрес
        else{
            //TODO сделать красивую статическую страничку 404
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Обрабатываем POST запрос.
     * @param request запрос
     * @param response ответ
     * @throws IOException TODO написать откуда может появиться!
     * @throws ServletException  TODO написать откуда может появиться!
     */
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        this.handleCount.getAndIncrement();
        response.setContentType("text/html;charset=utf-8");

        //пользователь пытается авторизоваться
        if (request.getPathInfo().equals(ADDRESS_AUTH))
        {
            String name = request.getParameter("login");

            //пользователь с таким именем существует
            //TODO добавить проверку пароля что ли
            if (this.users.containsKey(name))
            {
                //получаем id сессии и пользователя
                HttpSession session = request.getSession();
                String sessionId = session.getId();
                Long userId = this.users.get(name);
                //добавляем информацию о пользователе в сессию
                session.setAttribute("userId", userId);
                session.setAttribute("userName", name);
                //возвращаем  страницу с информацией о пользоватеде
                Map<String, Object> pageVariables = new HashMap<>();
                pageVariables.put("UserID", userId);
                pageVariables.put("Time", getTime());
                pageVariables.put("User", name);
                pageVariables.put("Session", sessionId);
                response.getWriter().println(PageGenerator.getPage("test.tml", pageVariables));
            }
            //пользователя не существует
            else
            {
                response.getWriter().println("Wrong username or password");
            }

            response.setStatus(HttpServletResponse.SC_OK);
        }
        //обращение к несуществующему адресу
        else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
