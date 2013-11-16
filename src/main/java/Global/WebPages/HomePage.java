package Global.WebPages;

import Global.WebPage;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 08.11.13
 * Time: 23:07
 *
 * Домашняя страница сайта. Соответствующий URL /home.
 */

public class HomePage extends WebPageImp implements WebPage {
    private static final int ENTRY = 0;

    private static final String TEMPLATE = "home.tml";

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        return ENTRY;
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        return ENTRY;
    }

    @SuppressWarnings("MethodMayBeStatic")
    @CaseHandler(routine = ENTRY, reqType = RequestType.ANY)
    public String handleEntry() {
        return generatePage(TEMPLATE);
    }
}
