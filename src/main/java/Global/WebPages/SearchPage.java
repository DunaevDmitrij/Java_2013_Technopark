package Global.WebPages;

import Global.WebPage;

import javax.servlet.http.HttpServletRequest;
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

    // Dopolnitel'nie peremennie
    // Context
    private Map<String, Object> pageVariables;

    // Constructor
    public SearchPage() {
        super();
        this.pageVariables = new HashMap<>();
    }

    @Override
    protected int analyzeRequestGET(HttpServletRequest request) {
        return JUST_LOOKING;
    }

    @Override
    protected int analyzeRequestPOST(HttpServletRequest request) {
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
        //TODO: Search, need DB
        this.pageVariables = dataToKey(new String[] {"PageTitle", "Location"},
                "Search Flight result","Yet empty :C");
        return generatePage("searchResult.tml",this.pageVariables);
    }
}
