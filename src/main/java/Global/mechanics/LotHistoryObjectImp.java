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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotHistoryObjectImp that = (LotHistoryObjectImp) o;

        if (!arg.equals(that.arg)) return false;
        if (!author.equals(that.author)) return false;
        if (!date.equals(that.date)) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + arg.hashCode();
        return result;
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
