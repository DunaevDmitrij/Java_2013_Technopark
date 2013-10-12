import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 12.10.13
 * Time: 11:02
 * To change this template use File | Settings | File Templates.
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
            String sessionId = (String) session.getId();

            // Заполняем контекст
            pageVariables = new HashMap<>();
            pageVariables.put("UserID", userId);
            pageVariables.put("Time", getTime());
            pageVariables.put("User", name);
            pageVariables.put("Session", sessionId);

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
        String name = (String) request.getParameter("login");

        // Проверка введенных данных
        if (users.containsKey(name)) {
            //получаем id сессии и пользователя
            HttpSession session = request.getSession();
            String sessionId = (String) session.getId();
            Long userId = (Long) users.get(name);

            //добавляем информацию о пользователе в сессию
            session.setAttribute("userId", userId);
            session.setAttribute("userName", name);

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put("UserID", userId);
            pageVariables.put("Time", getTime());
            pageVariables.put("User", name);
            pageVariables.put("Session", sessionId);

            return generatePage("test.tml", pageVariables);
        } else {
            return "Wrong username or password";
        }
    }
}
