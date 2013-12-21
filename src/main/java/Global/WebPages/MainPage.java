package Global.WebPages;

import Global.DBService;
import Global.Imps.UserSession;
import Global.SessionService;
import Global.WebPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dunaev (Started in AuthPage by Kislenko Maksim)
 * Date: 11/29/13
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 *
 * Athentication + basic information for user
 */

public class MainPage extends WebPageImp implements WebPage {

    // Sostojanija dl'a CaseHandler
    private static final int JUST_LOOKING = 0; // First time
    private static final int AUTHENTICATED_USER = 1; // If already authenicated
    private static final int CHECK_AUTH = 2; // Check data

    // Im'a templaita, s kotorim budet rabotat'
    private static final String TEMPLATE = "main.tml";

    // TODO: Hash with pages

    // Dopolnitel'nie peremennie
    // Context
    private Map<String, Object> pageVariables;

    // Authentication variables
    private final SessionService sessionService;
    private HttpSession session;
    private Long sessionId;
    private UserSession userSession = null;
    private String userName;

    // Constructor
    public MainPage(SessionService sessionService) {
        super();
        this.pageVariables = new HashMap<>();
        this.sessionService = sessionService;
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        this.session = request.getSession();
        this.sessionId = (Long) this.session.getAttribute("sessionId");

        if (this.sessionId == null) {
            // First time on site
            return JUST_LOOKING;
        } else {
            this.userSession = this.sessionService.getUserInfo(this.sessionId);
            return AUTHENTICATED_USER;
        }
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        HttpSession session = request.getSession();
        this.userName = request.getParameter("name");
        this.sessionId = (Long) session.getAttribute("sessionId");

        return CHECK_AUTH;
    }

    // Lol, da eto je metki ebanie. Nu ok, eto obrabotchik lubogo (ANY) zaprosa s kodom ENTRY
    @CaseHandler(routine = JUST_LOOKING, reqType = RequestType.GET)
    public String handleLooking() {
        //если это первый заход пользователя на сайт, присваиваем ему уникальный sessionId
        this.sessionId = this.sessionService.getNewSessionId();

        //передаем новый sessionId пользователю
        this.session.setAttribute("sessionId", this.sessionId);

        // Пользователь не авторизован
        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                "AviaDB","Welcome to AviaDB site!");
        return generatePage(TEMPLATE, this.pageVariables);
    }

    @CaseHandler(routine = AUTHENTICATED_USER, reqType = RequestType.GET)
    public String handleAuthenticated() {
        // If user is tried to authenticate
        if (this.userSession != null) {
            //ожидаем пока AccountService вернет данные
            if (this.userSession.isComplete()) {
                //проверяем, что пользователь существует
                if (! this.userSession.getUserId().equals(DBService.USER_NOT_EXIST)) {
                    System.out.println("Session Id: " + this.sessionId);

                    // Заполняем контекст
                    this.pageVariables = dataToKey(new String[] {"PageTitle", "Location","UserId", "UserName", "Time", "Session"},
                            "User Page","Your data",this.userSession.getUserId(), this.userSession.getName(), getTime(), this.sessionId);
                    return generatePage("temp.tml", this.pageVariables);

                } else {
                    this.sessionService.closeSession(this.sessionId); //удаляем текущую сессию
                    return "Error. No user with this name";
                }
            } else {
                //просим пользователя подождать
                //TODO: ----------------------------------------------------------------------------------------Remake waiting page
                return generatePage("wait.tml", new HashMap<String, Object>());
                //TODO: отправлять тут код ошибки с пустым телом или JSON, сообщающий, что идет поиск,
                //TODO: сделать на клиенте циклический опрос ограниченное кол-во раз, если ответа нет - сообщение
                //TODO: пользователю об ошибке/
            }
        }
        // Well, not authenticated
        else {
            this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                    "AviaDB","Welcome to AviaDB site!");
            return generatePage(TEMPLATE, this.pageVariables);
        }
    }

    @CaseHandler(routine = CHECK_AUTH, reqType = RequestType.POST)
    public String handleCheckAuth() {
        // Отправляем sessionId для построения нового объекта UserSession
        //TODO: здесь надо проверить пароль
        this.sessionService.createUserSession(this.sessionId, this.userName);

        // Отдаем страницу ожидания.
        return generatePage("wait.tml");
    }
}
