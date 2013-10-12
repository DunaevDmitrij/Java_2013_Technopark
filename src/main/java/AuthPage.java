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
class AuthPage extends WebPage {

    AuthPage() {
        this.Status = HttpServletResponse.SC_OK;
    }

    public String handleGET(HttpServletRequest request, Map<String, Long> users) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");

        Map<String, Object> pageVariables;
        if (userId == null) {
            pageVariables = new HashMap<>();
            return generatePage("auth.tml", pageVariables);
        } else {
            String name = (String) session.getAttribute("userName");
            String sessionId = (String) session.getId();

            pageVariables = new HashMap<>();
            pageVariables.put("UserID", userId);
            pageVariables.put("Time", getTime());
            pageVariables.put("User", name);
            pageVariables.put("Session", sessionId);

            return generatePage("test.tml", pageVariables);
        }
    }

    public String handlePOST(HttpServletRequest request, Map<String, Long> users) {
        String name = (String) request.getParameter("login");

        if (users.containsKey(name))
        {
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
        }
        //пользователя не существует
        else
        {
            return "Wrong username or password";
        }
    }
}
