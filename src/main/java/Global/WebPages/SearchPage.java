package Global.WebPages;

import Global.Address;
import Global.MessageSystem;
import Global.MsgSystem.Messages.MsgSearchRequest;
import Global.Ticket;
import Global.WebPage;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: dunaev
 * Date: 11/29/13
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 *
 * Page for searching flights
 */

public class SearchPage extends WebPageImp implements WebPage {
    // Sostojanija dl'a CaseHandler
    private static final int JUST_LOOKING = 0; // Just came to page
    private static final int SEARCHING = 1; // Started search

    // Im'a templaita, s kotorim budet rabotat'
    private static final String TEMPLATE = "search.tml";

    private MessageSystem ms;
    private Address frontendAddress;
    private boolean inSearch;
    private Collection<Ticket> tickets;

    // Dopolnitel'nie peremennie
    // Context
    private Map<String, Object> pageVariables;

    // Constructor
    public SearchPage(MessageSystem ms, Address frontendAddress) {
        super();
        this.pageVariables = new HashMap<>();
        this.inSearch = false;
        this.tickets = null;
        this.ms = ms;
        this.frontendAddress = frontendAddress;
    }

    public void update(Collection<Ticket> tickets) {
        this.inSearch = true;
        this.tickets = tickets;
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        return JUST_LOOKING;
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Address to = this.ms.getAddressService().getSalesMechanics();

        // TODO: parse request for query parameters

        this.ms.sendMessage(new MsgSearchRequest(this.frontendAddress, to, params));
        return SEARCHING;
    }

    @CaseHandler(routine = JUST_LOOKING, reqType = RequestType.GET)
    public String handleLooking() {
        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                "Search Flight","Enter criterias");
        return generatePage(TEMPLATE, this.pageVariables);
    }

    @CaseHandler(routine = SEARCHING, reqType = RequestType.POST)
    public String handleSearch() {

        while (this.tickets == null);

        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location", "results"},
                "Search Flight result", "", this.tickets);
        return generatePage("searchResult.tml",this.pageVariables);
    }
}
