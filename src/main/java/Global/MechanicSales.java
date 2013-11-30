package Global;

import java.util.Date;
import java.util.List;

/**
 * Author: artemlobachev
 * Date: 28.11.13
 */
public interface MechanicSales {
    public List<Ticket> search(String departureAirport, String arrivalAirport, Date departureSince);
    //TODO many search methods with different parametr sets or something more interesting?
    public boolean buy(Ticket ticket, User passenger);
}