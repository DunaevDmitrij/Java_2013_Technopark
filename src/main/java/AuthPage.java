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
 * Страница авторизации пользователя.
 * Наследует абстрактную веб-страницу.
 */
class AuthPage extends WebPage {
    private SessionService SS;

    public AuthPage(SessionService SS) {
        super();
        this.SS = SS;
    }

    /**
     * Переопределенный метод обработки GET запроса.
     * @param request объект запроса, для получения данных сессии
     * @return сгенерированная страница
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
            sessionId = SS.getSessionId();
            //передаем новый sessionId пользователю
            session.setAttribute("sessionId", sessionId);
            // Пользователь не авторизован
            pageVariables = new HashMap<>();

            return generatePage("auth.tml", pageVariables);

        } else {
            //пользователь заходит еще раз
            UserSession userSession = SS.getUserInfo(sessionId);

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
                        SS.closeSession(sessionId); //удаляем текущую сессию
                        return textPage("Такого пользователя нету");
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
     * @return сгенерированная страница
     */
    @Override
    public String handlePOST(HttpServletRequest request) {
        String userName = request.getParameter("name");

        //получаем sessionId
        HttpSession session = request.getSession();
        Long sessionId = (Long) session.getAttribute("sessionId");

        SS.createUserSession(sessionId, userName);
        return generatePage("wait.tml");
    }
}
