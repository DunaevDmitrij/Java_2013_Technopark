package Global;

import java.util.HashMap;

/**
 * Author: artemlobachev
 * Date: 28.11.13
 */
public interface MechanicSales {
    //TODO change Object to ticket/passenger where needed
    public Object search(HashMap<String,String> parametrs);
    public boolean buy(Object ticket, Object passenger);
}
