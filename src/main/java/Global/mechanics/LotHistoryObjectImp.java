package Global.mechanics;

import Global.LotHistoryObject;
import Global.User;

import java.util.Date;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class LotHistoryObjectImp implements LotHistoryObject{
    private final Date date;
    private final Type type;
    private final User author;
    private final String arg;

    public LotHistoryObjectImp(Date date, Type type, User author, String arg){
        this.date = date;
        this.type = type;
        this.author = author;
        this.arg = arg;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public User getAuthor() {
        return this.author;
    }

    @Override
    public String getArg() {
        return this.arg;
    }
}
