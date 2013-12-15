package Global.mechanics;

import Global.*;
import Global.MsgSystem.Abonent;

import java.util.*;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MechanicAuctionImp extends MechanicSalesImp implements MechanicAuction, Abonent, Runnable{
    //TODO: make own search and buy
    //TODO: task for closing lots

    public MechanicAuctionImp(MessageSystem ms){
        super(ms);
    }

    @Override
    public Collection<Ticket> search(Map<String, String> params){
        return new ArrayList<>();
    }

    @Override
    public boolean buy(Ticket ticket, User passenger){
        if (ticket instanceof Lot){
            Lot lot = (Lot)ticket;
            return false;
        }
        else
            throw new IllegalArgumentException("To buy tickets go to sales mechanic!");//Looks bad, but I don't know how to do it better :(
    }

    @Override
    public List<Object> getLots() {
        return null;
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
    protected void addMeToAddressService(){
        this.ms.getAddressService().setAuctionMechanics(this.address);
    }
}
