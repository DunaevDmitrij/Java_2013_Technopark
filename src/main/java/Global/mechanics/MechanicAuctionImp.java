package Global.mechanics;

import Global.*;
import Global.MsgSystem.Abonent;
import Global.MsgSystem.Messages.*;
import Global.ResSystem.ResourceSystem;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.sleep;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MechanicAuctionImp extends MechanicSalesImp implements MechanicAuction, Abonent, Runnable{
    //TODO: take DEFAULT_DELTA_BEFORE_CLOSING_TICKET_AND_DEPARTURE from resourses
    //TODO: tests (in pair with DBSERVICE)
    private static final String DEF_DELTA_BCLOSING_TICKET = "Default delta before closing ticket and departure";
    public static final long DEFAULT_DELTA_BEFORE_CLOSING_TICKET_AND_DEPARTURE = ResourceSystem.getRS().<Long> getParam(DEF_DELTA_BCLOSING_TICKET);//3 days

    public static final long CLOSE_TICKETS_EVERY_X_SECONDS = DEFAULT_DELTA_BEFORE_CLOSING_TICKET_AND_DEPARTURE;

    private static final long CLOSE_TICKETS_EVERY_X_TICKS = CLOSE_TICKETS_EVERY_X_SECONDS/SLEEP_TIME;

    private int ticksAfterLastLotClosing = 0;
    //find
    private final ConcurrentHashMap<Long, HashSet<Lot>> foundLots = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Boolean> foundLotStatuses = new ConcurrentHashMap<>();
    //buy
    private final ConcurrentHashMap<Long, Boolean> buyLotStatuses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Boolean> BuyLotResults = new ConcurrentHashMap<>();
    //add
    private final ConcurrentHashMap<Long, Boolean> addLotStatuses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Boolean> addLotResults = new ConcurrentHashMap<>();
    //risePrise
    private final ConcurrentHashMap<Long, Boolean> riseLotPriceStatuses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Boolean> riseLotPriceResults = new ConcurrentHashMap<>();

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
        long requestId = this.findFreeKey(this.buyLotStatuses);
        MsgBuyLot msg = new MsgBuyLot(this.address, this.ms.getAddressService().getAccountService(), lot, user, requestId);
        this.buyLotStatuses.put(requestId, false);
        this.ms.sendMessage(msg);
        while (!this.buyLotStatuses.get(requestId)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean result = this.BuyLotResults.get(requestId);
        this.BuyLotResults.remove(requestId);
        this.buyLotStatuses.remove(requestId);
        return result;
    }

    @Override
    public boolean addLot(Ticket ticket, int startPrice) {
        return this.addLot(ticket, startPrice, new Date(ticket.getDepartureDateTime().getTime() - DEFAULT_DELTA_BEFORE_CLOSING_TICKET_AND_DEPARTURE));
    }

    @Override
    public boolean addLot(Ticket ticket, int startPrice, Date stopSales) {
        long requestId = this.findFreeKey(this.addLotStatuses);
        MsgAddLot msg = new MsgAddLot(this.address, this.ms.getAddressService().getAccountService(),requestId,ticket,startPrice,stopSales);
        this.addLotStatuses.put(requestId, false);
        this.ms.sendMessage(msg);
        while (!this.addLotStatuses.get(requestId)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean result = this.addLotResults.get(requestId);
        this.addLotResults.remove(requestId);
        this.addLotStatuses.remove(requestId);
        return result;
    }

    @Override
    public boolean riseLotPrice(Lot lot, User user, int newPrice) {
        long requestId = this.findFreeKey(this.riseLotPriceStatuses);
        MsgRiseLotPrice msg = new MsgRiseLotPrice(this.address, this.ms.getAddressService().getAccountService(),requestId,lot,user,newPrice);
        this.riseLotPriceStatuses.put(requestId, false);
        this.ms.sendMessage(msg);
        while (!this.riseLotPriceStatuses.get(requestId)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean result = this.riseLotPriceResults.get(requestId);
        this.riseLotPriceResults.remove(requestId);
        this.riseLotPriceStatuses.remove(requestId);
        if (result){
            lot.risePrice(user, new Date(System.currentTimeMillis()), newPrice);
            MsgAddLotHistoryObject msgHist = new MsgAddLotHistoryObject(this.address, this.ms.getAddressService().getAuctionMechanics(),lot,new LotHistoryObjectImp(new Date(System.currentTimeMillis()), LotHistoryObject.Type.RISE_PRICE,user, String.valueOf(newPrice)));
            this.ms.sendMessage(msgHist);
        }
        return result;
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

    @Override
    public void lotBought(long requestId, boolean result){
        this.BuyLotResults.put(requestId, result);
        this.buyLotStatuses.put(requestId, true);
    }

    @Override
    public void lotAdded(long requestId, boolean result) {
        this.addLotResults.put(requestId,result);
        this.addLotStatuses.put(requestId,true);
    }

    @Override
    public void lotPriceRisen(long requestId, boolean result) {
        this.riseLotPriceResults.put(requestId,result);
        this.riseLotPriceStatuses.put(requestId,true);
    }


    @Override
    protected void addMeToAddressService(){
        this.ms.getAddressService().setAuctionMechanics(this.address);
    }

    public void closeLotsByTimeService(){
        this.ms.sendMessage(new MsgCloseLotsByTime(this.address, this.ms.getAddressService().getAccountService()));

    }

    @Override
    public void run(){
        while (true) {
            this.ms.execForAbonent(this);
            this.ticksAfterLastLotClosing++;
            if(this.ticksAfterLastLotClosing == CLOSE_TICKETS_EVERY_X_TICKS){
                this.closeLotsByTimeService();
                this.ticksAfterLastLotClosing = 0;
                System.out.println("Clearing task started");
            }
            try {
                sleep(SLEEP_TIME*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
