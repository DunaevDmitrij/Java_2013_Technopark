import Global.Lot;
import Global.LotHistoryObject;
import Global.Ticket;
import Global.User;
import Global.mechanics.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class LotImpTest {
    private final String owner = "Artem";
    private final String departureAirport = "DME"; //Moscow Domodedovo :)
    private final String arrivalAirport = "MIA";
    private Lot lot;
    private final int lastPrice = 12;
    private final int LotHistoryObjectsCount = 2;
    private final Date lastRiseDate = new Date(1386115200);
    private final User userVasia = new UserImp("Vasia");
    private final User userValera = new UserImp("Valera");


    @Before
    public void setUp(){

        ArrayList<SingleTicket> singleTickets = new ArrayList<>(3);
        //date = 7.12.13 15:00, flight time = 14hr
        SingleTicket singleTicket = new SingleTicket(this.departureAirport, "Vegas",new Date(1386428400),50400,"hangover-1", Ticket.seatClass.SEAT_CLASS_FIRST,"Boing 747-400",1);
        singleTickets.add(singleTicket);
        //date = 10.12.13 12:00, flight time = 12hr
        singleTicket = new SingleTicket("Vegas", "Bangkok",new Date(1386676800),43200,"hangover-2", Ticket.seatClass.SEAT_CLASS_ECONOMIC,"Boing 737-800",2);
        singleTickets.add(singleTicket);
        //date = 15.12.13 10:00, flight time = 5hr
        singleTicket = new SingleTicket("Bangkok", this.arrivalAirport,new Date(1387101600),18000,"hangover-3", Ticket.seatClass.SEAT_CLASS_FIRST,"Airbus A330",3);
        singleTickets.add(singleTicket);
        //total time = 8d = 691200 s
        TicketImp ticketImp = new TicketImp(this.owner, singleTickets, false, 0L);
        //startDate = 2 december 2013, 00:00
        //closeDate = 5 december 2013, 00:00
        this.lot = new LotImp(ticketImp, new Date(1385942400), new Date(1386201600),10);
        //date = 3 december 2013, 00:00
        this.lot.risePrice(userVasia,new Date(1386028800),11);
        //data = 4 december 2013, 00:00
        this.lot.risePrice(userValera,this.lastRiseDate,this.lastPrice);
    }

    @Test
    public void testGetCurrentPrice(){
        final String ErrText = "Lot.getCurrentPrice() works wrong.";
        Assert.assertEquals(ErrText, this.lot.getCurrentPrice(), this.lastPrice);

    }

    @Test
    public void testGetHistoryDESC(){
        final String ErrText = "Lot.getHistoryDESC() works wrong.";
        List<LotHistoryObject> list = this.lot.getHistoryDESC(this.LotHistoryObjectsCount);
        Assert.assertTrue(ErrText, list.size()<=this.LotHistoryObjectsCount);
        LotHistoryObjectImp lotHistoryObjectImp = new LotHistoryObjectImp(this.lastRiseDate, LotHistoryObject.Type.RISE_PRICE, userValera, String.valueOf(this.lastPrice));
        Assert.assertEquals(ErrText, list.get(0), lotHistoryObjectImp);
    }
}
