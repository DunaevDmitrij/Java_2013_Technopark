import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 12.10.13
 * Time: 11:02
 */

/**
 * Страница авторизации пользователя. Для своей работы требует ссылку на класс SessionService.
 * Наследует абстрактную веб-страницу.
 */
class AuthPage extends WebPage {
    // Осведомленность. Используется для выборки данных пользователь--сессия
    private final SessionService sS;

    // конструктор
    public AuthPage(SessionService sS) {
        super();
        this.sS = sS;
    }

    /**
     * Переопределенный метод обработки GET запроса.
     * @param request объект запроса, для получения данных сессии
     * @return сгенерированная страница авторизации.
     */
    @Override
    public String handleGET(HttpServletRequest request) {
        // Объект с данными сессии
        HttpSession session = request.getSession();
        Long sessionId = (Long) session.getAttribute("sessionId");

        // Контейнер с контекстом страницы
        Map<String, Object> pageVariables;

        //если это первый заход пользователя на сайт, присваиваем ему уникальный sessionId
        if (sessionId == null) {
            sessionId = this.sS.getNewSessionId();
            //передаем новый sessionId пользователю
            session.setAttribute("sessionId", sessionId);
            // Пользователь не авторизован
            pageVariables = new HashMap<>();

            return generatePage("auth.tml", pageVariables);

        } else {
            //пользователь заходит еще раз
            UserSession userSession = this.sS.getUserInfo(sessionId);

            if (userSession != null) {
                //ожидаем пока AccountService вернет данные
                if (userSession.isComplete()) {
                    //проверяем, что пользователь существует
                    if (! userSession.getUserId().equals(AccountService.USER_NOT_EXIST)) {
                        // Заполняем контекст
                        System.out.println("Session Id: " + sessionId);

                        pageVariables = dataToKey(new String[] {"UserId", "UserName", "Time", "Session"},
                                userSession.getUserId(), userSession.getName(), getTime(), sessionId);
                        return generatePage("test.tml", pageVariables);

                    } else {
                        this.sS.closeSession(sessionId); //удаляем текущую сессию
                        return "Такого пользователя нет";
                    }
                } else {
                    //просим пользователя подождать
                    return generatePage("wait.tml", new HashMap<String, Object>());
                    //TODO: отправлять тут код ошибки с пустым телом или JSON, сообщающий, что идет поиск,
                    //TODO: сделать на клиенте циклический опрос ограниченное кол-во раз, если ответа нет - сообщение
                    //TODO: пользователю об ошибке/
                }
            } else {
                return generatePage("auth.tml");
            }
        }
    }

    /**
     * Переопределенный метод обработки POST запроса.
     * @param request параметр с данными запроса
     * @return сгенерированная страница авторизации
     */
    @Override
    public String handlePOST(HttpServletRequest request) {
        String userName = request.getParameter("name");

        // получаем sessionId
        HttpSession session = request.getSession();
        Long sessionId = (Long) session.getAttribute("sessionId");

        // Отправляем sessionId для построения нового объекта UserSession
        //TODO: здесь надо проверить пароль
        this.sS.createUserSession(sessionId, userName);

        // Отдаем страницу ожидания.
        return generatePage("wait.tml");
    }
}
