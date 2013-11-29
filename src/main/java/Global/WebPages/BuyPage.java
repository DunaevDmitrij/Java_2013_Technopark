package Global.WebPages;

import Global.WebPage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dunaev
 * Date: 11/29/13
 * Time: 12:43 PM
 * To change this template use File | Settings | File Templates.
 *
 * Page for buying tickets
 */

public class BuyPage extends WebPageImp implements WebPage {
    // Sostojanija dl'a CaseHandler
    private static final int JUST_LOOKING = 0; // Just came to page
    private static final int TICKET_INFO = 1; // Show information about ticket
    private static final int BUY = 2; // Send request to buy ticket

    // Im'a templaita, s kotorim budet rabotat'
    private static final String TEMPLATE = "buy.tml";

    // Dopolnitel'nie peremennie
    // Context
    private Map<String, Object> pageVariables;

    // Constructor
    public BuyPage() {
        super();
        this.pageVariables = new HashMap<>();
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {  // TODO: Work with GET request. If has -> TICKET_INFO
        return JUST_LOOKING;
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        return BUY;
    }

    @CaseHandler(routine = JUST_LOOKING, reqType = RequestType.GET)
    public String handleLooking() {
        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                "Buy ticket","Empty page");
        return generatePage("buyEmpty.tml", this.pageVariables);
    }

    @CaseHandler(routine = TICKET_INFO, reqType = RequestType.GET) // TODO: Work with DB
    public String handleTicketInfo() {
        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                "Buy ticket","Ticket Information");
        return generatePage(TEMPLATE, this.pageVariables);
    }

    @CaseHandler(routine = BUY, reqType = RequestType.POST)
    public String handleBuy() {
        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                "Search Flight result","Yet empty :C");
        return generatePage("searchResult.tml",this.pageVariables);
    }
}
