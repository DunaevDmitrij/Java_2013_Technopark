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
public class Frontend extends HttpServlet implements Abonent, Runnable {

    public Frontend(MessageSystem ms) {
        super();
        this.ms = ms;
        //получаем адрес для Frontend
        this.address = new Address();
        //регистрируем Frontend в MessageSystem
        ms.addService(this);
    }

    /**
     * Выводим количество обращений каждые 5 секунд.
     */
    @Override
    public void run() {
        while (true) {
            this.ms.execForAbonent(this);

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //имплементация интерфейса Abonent
    @Override
    public Address getAddress() {
        return this.address;
    }

    //добавление userId в sessionIdToUserSession
    public void setId(Long sessionId, Long userId) {
        UserSession userSession = this.sessionIdToUserSession.get(sessionId);
        if (userSession == null) {
            System.out.append("Can't find user session for: ").append(sessionId.toString());
            return;
        }
        userSession.setUserId(userId);
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

        response.setContentType("text/html;charset=utf-8");

        //если пользователь пришел на страницу авторизации
        if (request.getPathInfo().equals(ADDRESS_AUTH))
        {
            //получаем sessionId
            HttpSession session = request.getSession();
            Long sessionId = (Long) session.getAttribute("sessionId");

            //если это первый заход пользователя на сайт, присваиваем ему уникальный sessionId
            if (sessionId == null)
            {
                //создаем новый sessionId
                sessionId = this.sessionIdCounter.getAndIncrement();
                //передаем sessionId пользователю
                session.setAttribute("sessionId", sessionId);
                //TODO: тут что-то было раньше :)
                response.getWriter().println(PageGenerator.getPage("auth.tml", new HashMap<String, Object>()));
            }
            //пользователь заходит еще раз
            else
            {
                if (this.sessionIdToUserSession.get(sessionId) != null)
                {
                    //если AccountService еще не отработал, userId = -1L
                    if (this.sessionIdToUserSession.get(sessionId).getUserId() > -1L)
                    {
                        Map<String, Object> pageVariables = new HashMap<>();
                        pageVariables.put("UserId", this.sessionIdToUserSession.get(sessionId).getUserId());
                        pageVariables.put("UserName", this.sessionIdToUserSession.get(sessionId).getName());
                        response.getWriter().println(PageGenerator.getPage("test.tml", pageVariables));
                    }
                    else if (this.sessionIdToUserSession.get(sessionId).getUserId() == -1L)
                    {
                        //такого пользователя нет
                        response.getWriter().println("Такого пользователя нету");
                    }
                    else
                    {
                        //просим пользователя подождать
                        response.getWriter().println(PageGenerator.getPage("wait.tml", new HashMap<String, Object>()));
                    }
                }
                else //пользователь не ввел имя (например просто обновил страницу)
                {
                    //требуем ввода имени
                    response.getWriter().println(PageGenerator.getPage("auth.tml", new HashMap<String, Object>()));
                }
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
     * @param request
     * @param response
     * @throws IOException TODO написать откуда может появиться!
     * @throws ServletException  TODO написать откуда может появиться!
     */
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException
    {
        //получаем sessionId
        HttpSession session = request.getSession();
        Long sessionId = (Long) session.getAttribute("sessionId");

        response.setContentType("text/html;charset=utf-8");

        //пользователь пытается авторизоваться
        if (request.getPathInfo().equals(ADDRESS_AUTH))
        {
            String userName = request.getParameter("name");
            //Создаем новую запись userSession
            UserSession userSession = new UserSession(sessionId, userName);
            //добавляем в sessionIdToUserSession
            this.sessionIdToUserSession.put(sessionId, userSession);

            Address frontendAddress = this.getAddress();
            Address accountServiceAddress = this.ms.getAddressService().getAccountService();

            this.ms.sendMessage(new MsgGetUserId(frontendAddress, accountServiceAddress, userName, sessionId));

            response.getWriter().println(PageGenerator.getPage("wait.tml", new HashMap<String, Object>()));

            response.setStatus(HttpServletResponse.SC_OK);
        }
        //обращение к несуществующему адресу
        else{
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    //TODO: определится со стилем, либо переменные в начале файла, либо в конце
    protected static final String ADDRESS_AUTH = "/auth";

    private final MessageSystem ms;
    private final AtomicLong sessionIdCounter = new AtomicLong();
    private final Map<Long, UserSession> sessionIdToUserSession = new HashMap<>();
    private final Address address;
}
