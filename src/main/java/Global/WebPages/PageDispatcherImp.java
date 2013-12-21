package Global.WebPages;

import Global.Address;
import Global.MessageSystem;
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
    private final MessageSystem ms;
    private final Address frontendAddress;

    private Map<String, WebPageImp> pages;

    public PageDispatcherImp(MessageSystem ms, SessionService sessionService, Address frontendAddress) {
        super();
        this.ms = ms;
        this.frontendAddress = frontendAddress;
        this.sessionService = sessionService;
        this.createPages();
    }

    private void createPages() {
        this.pages = new HashMap<>();
        this.pages.put(URL_AUTH, new AuthPage(this.sessionService));
        this.pages.put(URL_ADMIN, new AdminPage());
        this.pages.put(URL_MAIN, new MainPage(this.sessionService));
        this.pages.put(URL_SEARCH, new SearchPage(this.ms, this.frontendAddress));
        this.pages.put(URL_BUY, new BuyPage());
        this.pages.put(URL_ACCOUNT, new AccountPage());
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
