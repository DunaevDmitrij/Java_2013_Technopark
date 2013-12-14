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
    // Hash for URLs in frontend
    Map<String, String> pagesHash = new HashMap<>();

    public PageDispatcherImp(SessionService sessionService) {
        super();
        this.sessionService = sessionService;
        this.createPages();
    }

    private void createPages() {
        this.pages = new HashMap<>();
        this.pagesHash= new HashMap<>();
        this.pages.put(URL_HOME, new HomePage());
        this.pagesHash.put(URL_HOME,"Homepage");
        this.pages.put(URL_AUTH, new AuthPage(this.sessionService));
        this.pagesHash.put(URL_AUTH,"Authentication page");
        this.pages.put(URL_ADMIN, new AdminPage());
        this.pagesHash.put(URL_ADMIN,"Admin Page ~");
        this.pages.put(URL_FROM_THE_VERY_BOTTOM_OF_MY_HEARTH, new FromTheVeryBottomOfMyHearth());
        this.pagesHash.put(URL_FROM_THE_VERY_BOTTOM_OF_MY_HEARTH,"Testpage C:");
        this.pages.put(URL_MAIN, new MainPage(this.sessionService));
        this.pagesHash.put(URL_MAIN,"Main page");
        this.pages.put(URL_SEARCH, new SearchPage());
        this.pagesHash.put(URL_SEARCH,"Flight Search");
        this.pages.put(URL_BUY, new BuyPage());
        this.pagesHash.put(URL_BUY,"Buying page");
        this.pages.put(URL_ACCOUNT, new AccountPage());
        this.pagesHash.put(URL_ACCOUNT,"Account page");
        this.pages.put(URL_AUCTION, new AuctionPage());
        this.pagesHash.put(URL_AUCTION,"Auction page");
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
