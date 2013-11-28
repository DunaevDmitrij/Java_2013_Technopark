package Global;

import java.util.Date;
import java.util.List;

/**
 * Author: artemlobachev
 * Date: 28.11.13
 */
public interface MechanicAuction extends MechanicSales {
    //TODO change Object to ticket/passenger/lot where needed
    public List<Object> getLots();
    public boolean addLot(Object ticket, int startPrice);
    public boolean addLot(Object ticket, int startPrice, Date stopSales);
    public boolean riseLotPrice(Object lot, int newPrice);
    //TODO not to forget to close lots by time
}
