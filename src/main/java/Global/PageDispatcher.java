package Global;

import Global.WebPages.WebPageImp;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 14.11.13
 * Time: 20:36
 */

public interface PageDispatcher {
    // Здесь и далее: все URL-ы записываются как константы.
    String URL_AUTH = "/auth";
    String URL_ADMIN = "/admin";
    String URL_MAIN = "/main";
    String URL_SEARCH = "/search";
    String URL_BUY = "/buy";
    String URL_ACCOUNT = "/account";


    WebPageImp getPage(String url);

    String redirect(String url, HttpServletRequest request);
}
