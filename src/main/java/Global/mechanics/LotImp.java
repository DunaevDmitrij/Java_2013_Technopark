package Global.mechanics;

import Global.Lot;
import Global.LotHistoryObject;
import Global.Ticket;
import Global.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class LotImp extends TicketImp implements Lot {
    private Date closeDate;
    private List<LotHistoryObject> history;

    public LotImp(Ticket ticket, Date closeDate){
        super(ticket.getOwner(),ticket.getRoute(),ticket.isTemporary());
        this.closeDate = closeDate;
        this.history = new ArrayList<>();
        this.history.add(new LotHistoryObjectImp(new Date(System.currentTimeMillis()), LotHistoryObject.Type.OPEN,new UserImp("Valera"),String.valueOf(this.getPrice())));
    }

    @Override
    public boolean risePrice(User user, int newPrice) {
        return false;//TODO
    }

    @Override
    public int getCurrentPrice() {
        return 0;//TODO
    }

    @Override
    public Date getCloseDate() {
        return this.closeDate;
    }

    @Override
    public List<LotHistoryObject> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public List<LotHistoryObject> getHistoryDESC(int maxItems) {
        ArrayList<LotHistoryObject> rez = new ArrayList<>(maxItems);
        int currentSize = 0;
        while (currentSize<maxItems && currentSize<this.history.size()){
            rez.add(this.history.get(this.history.size() - 1 - currentSize));
        }
        return rez;
    }
}
