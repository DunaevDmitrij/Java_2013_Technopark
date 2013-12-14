package Global.mechanics;

import Global.*;
import Global.MsgSystem.Abonent;
import Global.MsgSystem.Messages.MsgBuyTicket;
import Global.MsgSystem.Messages.MsgFindTicket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class MechanicSalesImp implements MechanicSales, Abonent, Runnable {
    private final Address address;
    private final MessageSystem ms;
    private final ConcurrentHashMap<Long, Boolean> foundTicketStatuses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, HashSet<SingleTicket>> foundTicketResults = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Boolean> buyRequestsStatuses = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Boolean> BuyRequestsResults = new ConcurrentHashMap<>();

    public MechanicSalesImp(MessageSystem ms) {
        super();
        this.ms = ms;
        this.address = new Address();
        this.ms.getAddressService().setSalesMechanics(this.address);
        this.ms.addService(this);
    }

    @Override
    public Collection<Ticket> search(Map<String, String> params) {
        //TODO make real search: building tree, etc
        long requestId = this.getNewSearchRequestId();
        this.foundTicketStatuses.put(requestId,false);
        MsgFindTicket msg = new MsgFindTicket(this.address, this.ms.getAddressService().getAccountService(), params, requestId);
        this.ms.sendMessage(msg);
        while (!this.foundTicketStatuses.get(requestId)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ArrayList<SingleTicket> singleTickets = new ArrayList<>();
        singleTickets.add(this.foundTicketResults.get(requestId).iterator().next());
        Ticket tmpTicket = new TicketImp(singleTickets,false);
        ArrayList<Ticket> rez = new ArrayList<>();
        rez.add(tmpTicket);
        //removing used values
        this.foundTicketStatuses.remove(requestId);
        this.foundTicketResults.remove(requestId);
        return rez;
    }

    @Override
    public boolean buy(Ticket ticket, User passenger) {
        long requestId = this.getNewBuyRequestId();
        MsgBuyTicket msg = new MsgBuyTicket(this.address, this.ms.getAddressService().getAccountService(), ticket, passenger, requestId);
        this.buyRequestsStatuses.put(requestId, false);
        this.ms.sendMessage(msg);
        while (!this.buyRequestsStatuses.get(requestId)){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean result = this.BuyRequestsResults.get(requestId);
        this.BuyRequestsResults.remove(requestId);
        this.buyRequestsStatuses.remove(requestId);
        return result;
    }

    @Override
    public void ticketsFound(long requestId, Collection<SingleTicket> tickets) {
        //TODO: check if has answer
        HashSet<SingleTicket> singleTickets = new HashSet<>();
        for(SingleTicket ticket:tickets){
            singleTickets.add(ticket);
        }
        this.foundTicketResults.put(requestId, singleTickets);
        this.foundTicketStatuses.put(requestId,true);
    }

    @Override
    public void ticketBought(long requestId, boolean result) {
        this.BuyRequestsResults.put(requestId,result);
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public void run() {

    }

    private long getNewSearchRequestId(){
        //TODO
        return 0;
    }

    private long getNewBuyRequestId(){
        //TODO
        return 0;
    }
}
