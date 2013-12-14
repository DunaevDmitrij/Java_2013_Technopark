package Global;

import java.util.List;

/**
 * Author: artemlobachev
 * Date: 29.11.13
 */
public interface Lot  extends Ticket{

    public boolean risePrice(User user, int newPrice);
    public List<LotHistoryObject> getHistory();
}
