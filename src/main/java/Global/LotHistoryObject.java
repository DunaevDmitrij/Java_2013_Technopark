package Global;

import java.util.Date;

/**
 * Author: artemlobachev
 * Date: 29.11.13
 */
public interface LotHistoryObject {
    public static enum type{
        OPEN, CLOSE, RISE_PRICE
    }

    public Date getDate();
    public type getType();
    public User getAuthor();
    public String getArg();//something like new price for RISE_PRICE
}
