package Global.WebPages;

import Global.WebPage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dunaev
 * Date: 11/29/13
 * Time: 12:54 PM
 * To change this template use File | Settings | File Templates.
 *
 * Page for every user
 */

public class AccountPage extends WebPageImp implements WebPage { // TODO: Connect with Main page!!!
    // Sostojanija dl'a CaseHandler
    private static final int JUST_LOOKING = 0; // Just came to page

    // Im'a templaita, s kotorim budet rabotat'
    private static final String TEMPLATE = "account.tml";

    // Dopolnitel'nie peremennie
    // Context
    private Map<String, Object> pageVariables;

    // Constructor
    public AccountPage() {
        super();
        this.pageVariables = new HashMap<>();
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        return JUST_LOOKING;
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        return JUST_LOOKING;
    }

    @CaseHandler(routine = JUST_LOOKING, reqType = RequestType.GET)
    public String handleLooking() {
        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                "Acoount","Your personall page");
        return generatePage(TEMPLATE, this.pageVariables);
    }
}
