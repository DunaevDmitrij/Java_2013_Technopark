package Global.WebPages;

import Global.PageDispatcher;
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

public class PageDispatcherImp implements PageDispatcher {
    private final SessionService sessionService;
    private Map<String, WebPageImp> pages;

    public PageDispatcherImp(SessionService sessionService) {
        super();
        this.sessionService = sessionService;
        this.createPages();
    }

    private void createPages() {
        this.pages = new HashMap<>();
        this.pages.put(URL_HOME, new HomePage());
        this.pages.put(URL_AUTH, new AuthPage(this.sessionService));
        this.pages.put(URL_ADMIN, new AdminPage());
        this.pages.put(URL_FROM_THE_VERY_BOTTOM_OF_MY_HEARTH, new FromTheVeryBottomOfMyHearth());
    }

    @Override
    public WebPageImp getPage(String url) {
        return this.pages.get(url);
    }

    @Override
    public String redirect(String url, HttpServletRequest request) {
        WebPageImp target = this.getPage(url);
        return target.handleGET(request);
    }

}
