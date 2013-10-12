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

    /**
     * Переопределенный метод обработки GET запроса.
     * @param request объект запроса, для получения данных сессии
     * @param users  контейнер пользователей для проверки прав
     * @return сгенерированная страница
     */
    @Override
    public String handleGET(HttpServletRequest request, Map<String, Long> users) {
        // Объект с данными сессии
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        // Контейнер с контекстом страницы
        Map<String, Object> pageVariables;
        if (userId == null) {
            // Пользователь не авторизован
            pageVariables = new HashMap<>();
            return generatePage("auth.tml", pageVariables);
        } else {
            // Авторизация прошла
            String name = (String) session.getAttribute("userName");
            String sessionId = session.getId();

            // Заполняем контекст
            pageVariables = dataToKey(new String[] {"UserID", "Time",    "User", "Session"},
                                                     userId,  getTime(),  name,   sessionId);

            return generatePage("test.tml", pageVariables);
        }
    }

    /**
     * Переопределенный метод обработки POST запроса.
     * @param request параметр с данными запроса
     * @param users контейнер с учетными данными пользователей
     * @return сгенерированная страница
     */
    @Override
    public String handlePOST(HttpServletRequest request, Map<String, Long> users) {
        String name = request.getParameter("login");

        // Проверка введенных данных
        if (users.containsKey(name)) {
            //получаем id сессии и пользователя
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            Long userId = users.get(name);

            //добавляем информацию о пользователе в сессию
            session.setAttribute("userId", userId);
            session.setAttribute("userName", name);

            // Заполнение контекста
            Map<String, Object> pageVariables;
            pageVariables = dataToKey(new String[] {"UserID", "Time",    "User", "Session"},
                                                     userId,  getTime(),  name,   sessionId);

            return generatePage("test.tml", pageVariables);
        } else {
            return "Wrong username or password";
        }
    }
}
