package Global;

import Global.mechanics.SingleTicket;

import java.util.Collection;
import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 28.11.13
 */
public interface MechanicSales {
    public interface findParams{
        String ARRIVAL_AIRPORT = "ArrivalAirport";
        String DEPARTURE_AIRPORT = "DepartureAirport";
        String DEPARTURE_DATE_TIME_SINCE = "DepertureDateTimeSince";
        String DEPARTURE_DATE_TIME_TO = "DepertureDateTimeTo";
        String MAX_FLIGHT_TIME = "FlightTime";
        String MIN_SEAT_CLASS = "MinSeatClass";
    }

    public Collection<Ticket> search(Map<String, String> params);
    //TODO many search methods with different parametr sets or something more interesting?
    public boolean buy(Ticket ticket, User passenger);

    public void ticketsFound(long requestId, Collection<SingleTicket> tickets);
}
