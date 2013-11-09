package Global.WebPages;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 08.11.13
 * Time: 23:07
 */
public class HomePage extends WebPageImp {

    private static final int ENTRY = 0;

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        return ENTRY;
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        return ENTRY;
    }

    @CaseHandler(routine = ENTRY, reqType = RequestType.ANY)
    public String handleEntry() {
        return generatePage("home.tml");
    }
}
