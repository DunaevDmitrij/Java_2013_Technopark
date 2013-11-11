package Global.WebPages;

import Global.SessionService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 08.11.13
 * Time: 23:07
 */

public class PageDispatcher {
    private final SessionService sessionService;
    private Map<String, WebPageImp> pages;


    /*
    TODO: предполагаю, что эти константы нам понадобятся вне этого класса, поэтому надо сделать для него интерфейс и внутри него сделать эти константы доступными
    пример, где это может понадобиться: в Main для переадресации, в страницах для построения
    */
    // Здесь и далее: все URL-ы записываются как константы.
    private static final String URL_AUTH = "/auth";
    private static final String URL_HOME = "/home";
    private static final String URL_ADMIN_PAGE = "/admin";


    public PageDispatcher(SessionService sessionService) {
        super();
        this.sessionService = sessionService;
        this.createPages();
    }

    private void createPages() {
        this.pages = new HashMap<>();
        this.pages.put(URL_HOME, new HomePage());
        this.pages.put(URL_AUTH, new AuthPage(this.sessionService));
        this.pages.put(URL_ADMIN_PAGE, new AdminPage());
    }

    public WebPageImp getPage(String url) {
        return this.pages.get(url);
    }

    public String redirect(String url, HttpServletRequest request) {
        WebPageImp target = this.getPage(url);
        return target.handleGET(request);
    }

}
