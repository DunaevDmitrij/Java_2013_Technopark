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
        SingleTicket singleTicket = new SingleTicket(this.departureAirport, "Vegas",new Date(1386428400),50400000,"hangover-1", Ticket.seatClass.SEAT_CLASS_FIRST,"Boing 747-400");
        singleTickets.add(singleTicket);
        //date = 10.12.13 12:00, flight time = 12hr
        singleTicket = new SingleTicket("Vegas", "Bangkok",new Date(1386676800),43200000,"hangover-2", Ticket.seatClass.SEAT_CLASS_FIRST,"Boing 737-800");
        singleTickets.add(singleTicket);
        //date = 15.12.13 10:00, flight time = 5hr
        singleTicket = new SingleTicket("Bangkok", this.arrivalAirport,new Date(1387101600),18000,"hangover-3", Ticket.seatClass.SEAT_CLASS_FIRST,"Airbus A330");
        singleTickets.add(singleTicket);
        //total time = 8d = 691200 s
        this.ticketImp = new TicketImp(this.owner, singleTickets, false);
    }

    @Test
    public void testGetFlightTime() {
        final String errText = "Error in flight length counting";
        System.out.println(System.currentTimeMillis());
        Assert.assertEquals(errText,  691200, ticketImp.getFlightTime());
    }
}
