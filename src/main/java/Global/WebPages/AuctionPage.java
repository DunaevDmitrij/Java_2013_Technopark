package Global.WebPages;

import Global.WebPage;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dunaev
 * Date: 11/29/13
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 *
 * Page with auction system
 */
public class AuctionPage extends WebPageImp implements WebPage {
    // Sostojanija dl'a CaseHandler
    private static final int JUST_LOOKING = 0; // Just came to page
    private static final int LOOK_ITEM = 1; // Just came to page


    // Im'a templaita, s kotorim budet rabotat'
    private static final String TEMPLATE = "auction.tml";

    // Dopolnitel'nie peremennie
    // Context
    private Map<String, Object> pageVariables;

    // Constructor
    public AuctionPage() {
        super();
        this.pageVariables = new HashMap<>();
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) { // TODO: If something in GET -> show item
        return JUST_LOOKING;
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        return JUST_LOOKING;
    }

    @CaseHandler(routine = JUST_LOOKING, reqType = RequestType.GET)
    public String handleLooking() {
        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                "Auction","Choose your item to make a bet");
        return generatePage(TEMPLATE, this.pageVariables);
    }

    @CaseHandler(routine = LOOK_ITEM, reqType = RequestType.GET)
    public String handleLookItem() {
        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                "Item information","Information about this item");
        return generatePage(TEMPLATE, this.pageVariables);
    }
}

