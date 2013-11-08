package Global.WebPages;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 08.11.13
 * Time: 23:07
 */
public class HomePage extends WebPage {

    interface Routines {
        int ENTRY = 0;
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        return Routines.ENTRY;
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        return Routines.ENTRY;
    }

    @CaseHandler(routine = Routines.ENTRY, reqType = RequestType.ANY)
    public String handleEntry() {
        return generatePage("home.tml");
    }
}
