package Global.mechanics;

import Global.*;
import Global.MsgSystem.Abonent;
import Global.MsgSystem.Messages.MsgFindLot;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MechanicAuctionImp extends MechanicSalesImp implements MechanicAuction, Abonent, Runnable{
    //TODO: make own search and buy
    //TODO: task for closing lots
    private final ConcurrentHashMap<Long, HashSet<Lot>> foundLots = new ConcurrentHashMap<>();

    public MechanicAuctionImp(MessageSystem ms){
        super(ms);
    }

    @Override
    public Collection<Lot> searchLots (Map<String,String>params, boolean activeOnly, int maxResults) {
        long requestId = this.getNewSearchRequestId();
        this.foundTicketStatuses.put(requestId,false);
        MsgFindLot msg = new MsgFindLot(this.address, this.ms.getAddressService().getAccountService(), params, requestId);
        this.ms.sendMessage(msg);
        while (!this.foundTicketStatuses.get(requestId)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!this.foundLots.get(requestId).isEmpty()){
            HashSet<Lot> rez = foundLots.get(requestId);
            //removing used values
            this.foundTicketStatuses.remove(requestId);
            this.foundLots.remove(requestId);
            return rez;
        }
        //removing used values
        this.foundTicketStatuses.remove(requestId);
        this.foundLots.remove(requestId);
        return null;
    }

    @Override
    public boolean buyLot(Lot lot, User user) {
        return false;
    }

    @Override
    public boolean addLot(Ticket ticket, int startPrice) {
        return false;
    }

    @Override
    public boolean addLot(Ticket ticket, int startPrice, Date stopSales) {
        return false;
    }

    @Override
    public boolean riseLotPrice(Lot lot, User user, int newPrice) {
        return false;
    }

    @Override
    public void lotsFound(long requestId, Collection<Lot> tickets) {
        //TODO: check if has answer
        HashSet<Lot> singleTickets = new HashSet<>();
        for(Lot ticket:tickets){
            singleTickets.add(ticket);
        }
        this.foundLots.put(requestId, singleTickets);
        this.foundTicketStatuses.put(requestId,true);
    }

    @Override
    protected void addMeToAddressService(){
        this.ms.getAddressService().setAuctionMechanics(this.address);
    }
}
