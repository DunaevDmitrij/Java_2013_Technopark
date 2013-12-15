package Global;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 28.11.13
 */
public interface MechanicAuction extends MechanicSales {
    public Collection<Lot> searchLots (Map<String,String>params, boolean activeOnly, int maxResults);
    public boolean buyLot(Lot lot, User user);
    public boolean addLot(Ticket ticket, int startPrice);
    public boolean addLot(Ticket ticket, int startPrice, Date stopSales);
    public boolean riseLotPrice(Lot lot, User user, int newPrice);
    public void lotsFound(long requestId, Collection<Lot> tickets);
    //TODO not to forget to close lots by time
}
