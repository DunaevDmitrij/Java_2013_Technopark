package Global.WebPages;

import Global.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
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
    private Map<String, WebPage> pages;

    // Здесь и далее: все URL-ы записываются как константы.
    public interface Urls {
        String AUTH_ADRS = "/auth";
        String HOME_ADRS = "/home";
    }


    public PageDispatcher(SessionService sessionService) {
        super();
        this.sessionService = sessionService;
        this.createPages();
    }

    private void createPages() {
        this.pages = new HashMap<>();
        this.pages.put(Urls.HOME_ADRS, new HomePage());
        this.pages.put(Urls.AUTH_ADRS, new AuthPage(this.sessionService));
    }

    public WebPage getPage(String url) {
        return this.pages.get(url);
    }

    public String redirect(String url, HttpServletRequest request) {
        WebPage target = this.getPage(url);
        return target.handleGET(request);
    }

}
