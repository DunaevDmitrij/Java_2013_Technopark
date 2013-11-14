package Global;

import Global.WebPages.WebPageImp;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 14.11.13
 * Time: 20:36
 */

public interface PageDispatcher {
    // Здесь и далее: все URL-ы записываются как константы.
    String URL_AUTH = "/auth";
    String URL_HOME = "/home";
    String URL_ADMIN = "/admin";

    WebPageImp getPage(String url);

    String redirect(String url, HttpServletRequest request);
}
