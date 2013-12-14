package Global.mechanics;

import Global.*;
import Global.MsgSystem.Abonent;
import Global.MsgSystem.Messages.MsgFindTicket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class MechanicSalesImp implements MechanicSales, Abonent, Runnable {
    private final Address address = new Address();
    private final MessageSystem ms;
    private Map<Long, SingleTicket> foundTickets;

    public MechanicSalesImp(MessageSystem ms) {
        this.ms = ms;
    }

    @Override
    public Collection<Ticket> search(Map<String, String> params) {
        //TODO make real search: building tree, etc
        long requestId = this.getNewRequestId();
        foundTickets.put(new Long(requestId), null);
        MsgFindTicket msg = new MsgFindTicket(this.address, ms.getAddressService().getAccountService(), params, requestId);
        ms.sendMessage(msg);
        while (this.foundTickets.get(new Long(requestId)) == null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ArrayList<SingleTicket> singleTickets = new ArrayList<>();
        singleTickets.add(this.foundTickets.get(new Long(requestId)));
        Ticket tmpTicket = new TicketImp(singleTickets,false);
        ArrayList<Ticket> rez = new ArrayList<>();
        rez.add(tmpTicket);
        return rez;
    }

    @Override
    public boolean buy(Ticket ticket, User passenger) {
        return false;
    }

    @Override
    public void ticketsFound(long requestId, Collection<SingleTicket> tickets) {
        //TODO: check if has answer
        this.foundTickets.put(requestId,tickets.iterator().next());

    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public void run() {

    }

    private long getNewRequestId(){
        //TODO
        return 0;
    }
}
