import Global.Ticket;
import Global.mechanics.SingleTicket;
import Global.mechanics.TicketImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

/**
 * Author: artemlobachev
 * Date: 07.12.13
 */
public class TicketImpTest {
    private final String owner = "Artem";
    private final String departureAirport = "DME"; //Moscow Domodedovo :)
    private final String arrivalAirport = "MIA"; //Miami International Airpotr ;)
    private TicketImp ticketImp;

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
        this.ticketImp = new TicketImp(this.owner, singleTickets, false, 0L);
    }

    @Test
    public void testGetFlightTime() {
        final String errText = "Error in flight length counting";
        Assert.assertEquals(errText,  691200, ticketImp.getFlightTime());
    }

    @Test
    public void testGetMinSeatClass(){
        final String errText = "Error in getMinSeatClass()";
        Assert.assertEquals(errText, Ticket.seatClass.SEAT_CLASS_ECONOMIC, this.ticketImp.getMinSeatClass());

    }

    @Test
    public void  testGetDepartureAirport(){
        final String errText = "Error in getDepartureAirport()";
        Assert.assertEquals(errText, this.departureAirport, this.ticketImp.getDepartureAirport());
    }

    @Test
    public void  testGetArrivaAirport(){
        final String errText = "Error in getArrivalAirport()";
        Assert.assertEquals(errText, this.arrivalAirport, this.ticketImp.getArrivalAirport());
    }
}
