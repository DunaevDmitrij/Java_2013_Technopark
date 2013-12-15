package Global.mechanics;

import Global.*;
import Global.MsgSystem.Abonent;
import Global.MsgSystem.Messages.MsgBuyLot;
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
    //TODO: task for closing lots
    //TODO: addLot, riseLotPrice
    public static final long DEFAULT_DELTA_BEFORE_CLOSING_TICKET_AND_DEPARTURE = 259200;//3 days
    private final ConcurrentHashMap<Long, HashSet<Lot>> foundLots = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Boolean> foundLotStatuses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Boolean> buyLotRequestsStatuses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Boolean> BuyLotRequestsResults = new ConcurrentHashMap<>();

    public MechanicAuctionImp(MessageSystem ms){
        super(ms);
    }

    @Override
    public Collection<Lot> searchLots (Map<String,String>params, boolean activeOnly, int maxResults) {
        long requestId = this.findFreeKey(this.foundLotStatuses);
        this.foundLotStatuses.put(requestId, false);
        MsgFindLot msg = new MsgFindLot(this.address, this.ms.getAddressService().getAccountService(), params, requestId);
        this.ms.sendMessage(msg);
        while (!this.foundLotStatuses.get(requestId)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!this.foundLots.get(requestId).isEmpty()){
            HashSet<Lot> rez = foundLots.get(requestId);
            //removing used values
            this.foundLotStatuses.remove(requestId);
            this.foundLots.remove(requestId);
            return rez;
        }
        //removing used values
        this.foundLotStatuses.remove(requestId);
        this.foundLots.remove(requestId);
        return null;
    }

    @Override
    public boolean buyLot(Lot lot, User user) {
        long requestId = this.findFreeKey(this.buyLotRequestsStatuses);
        MsgBuyLot msg = new MsgBuyLot(this.address, this.ms.getAddressService().getAccountService(), lot, user, requestId);
        this.buyLotRequestsStatuses.put(requestId, false);
        this.ms.sendMessage(msg);
        while (!this.buyLotRequestsStatuses.get(requestId)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean result = this.BuyLotRequestsResults.get(requestId);
        this.BuyLotRequestsResults.remove(requestId);
        this.buyLotRequestsStatuses.remove(requestId);
        return result;
    }

    @Override
    public boolean addLot(Ticket ticket, int startPrice) {
        return this.addLot(ticket, startPrice, new Date(ticket.getDepartureDateTime().getTime() - DEFAULT_DELTA_BEFORE_CLOSING_TICKET_AND_DEPARTURE));
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
        HashSet<Lot> singleTickets = new HashSet<>();
        for(Lot ticket:tickets){
            singleTickets.add(ticket);
        }
        this.foundLots.put(requestId, singleTickets);
        this.foundLotStatuses.put(requestId, true);
    }

    public void lotBought(long requestId, boolean result){
        this.BuyLotRequestsResults.put(requestId,result);
        this.buyLotRequestsStatuses.put(requestId,true);
    };

    @Override
    protected void addMeToAddressService(){
        this.ms.getAddressService().setAuctionMechanics(this.address);
    }
}
