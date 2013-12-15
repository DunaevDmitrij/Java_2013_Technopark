package Global;

import java.util.Date;
import java.util.List;

/**
 * Author: artemlobachev
 * Date: 29.11.13
 */
public interface Lot  extends Ticket{
    public boolean risePrice(User user, Date date, int newPrice);
    public int getCurrentPrice();
    public Date getCloseDate();
    public List<LotHistoryObject> getHistory();
    public List<LotHistoryObject> getHistoryDESC(int maxItems);
}
