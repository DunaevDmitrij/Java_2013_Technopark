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
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: artemlobachev
 * Date: 21.09.13
 * Time: 10:22
 * To change this template use File | Settings | File Templates.
 */
public class Frontend extends HttpServlet implements ExtRunnable {

    private final static String AUTH_PAGE_ADDRESS = "/test";
    private final static String AUTH_POST_ADDRESS = "/auth";

    private Map<String, Long> users;
    private AtomicLong userIdGenerator = new AtomicLong();

    // Счетчик обработанных запросов
    private int handleCount;

    // Ссылка на поток, в котором выполняется Frontend.run()
    // Осведомленность
    private ExtThread thisThread;


    /**
     * Инициализирует подключение к БД пользователей, или пока имитирующему Map.
     * Добавляет пользователей к Map.
     */
    public Frontend() {
        // Инициализация полей
        users = new HashMap<>();
        handleCount = 0;
        thisThread = null;

        // Добавление записей о пользователях
        users.put("vasia", 0L);
        users.put("valera", 1L);
    }

    /**
     * Метод для команд, выполняемых Frontend в собственном потоке
     */
    @Override
    public void run() {
        // Проверка существования потока
        if (thisThread != null) {
            while (thisThread.isAlive()) {
                // Вывод значения счетчика в лог (отсылка к ThreadPool)
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("Frontend выполнние прервано.");
                }
                thisThread.toPool("Текущий handleCount = " + handleCount);
            }
        }
    }

    public void setThread(ExtThread thread) {
        thisThread = thread;
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

        //если пользователь пришел на страницу авторизации
        if (request.getPathInfo().equals(AUTH_PAGE_ADDRESS)){

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
                String sessionId = (String) session.getId();
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
        handleCount++;
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

        //пользователь пытается авторизоваться
        if (request.getPathInfo().equals(AUTH_POST_ADDRESS))
        {
            String name = (String) request.getParameter("login");

            //пользователь с таким именем существует
            //TODO добавить проверку пароля что ли
            if (users.containsKey(name))
            {
                //получаем id сессии и пользователя
                HttpSession session = request.getSession();
                String sessionId = (String) session.getId();
                Long userId = (Long) users.get(name);
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

        handleCount++;
    }

    /**
     *
     * @return текущее время в заданном формате.
     */
    private static String getTime() {
        Date date = new Date();
        date.getTime();
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss"); // здесь задаем формат времени
        return formatter.format(date);
    }
}
