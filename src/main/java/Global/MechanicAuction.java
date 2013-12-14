package Global;

import java.util.Date;
import java.util.List;

/**
 * Author: artemlobachev
 * Date: 28.11.13
 */
public interface MechanicAuction extends MechanicSales {
    public List<Object> getLots();
    public boolean addLot(Ticket ticket, int startPrice);
    public boolean addLot(Ticket ticket, int startPrice, Date stopSales);
    public boolean riseLotPrice(Lot lot, User user, int newPrice);
    //TODO not to forget to close lots by time
}
