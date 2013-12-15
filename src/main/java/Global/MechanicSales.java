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
        String DEPARTURE_DATE_TIME_SINCE = "DepartureDateTimeSince";
        String DEPARTURE_DATE_TIME_TO = "DepartureDateTimeTo";
        String MAX_FLIGHT_TIME = "FlightTime";
        String MIN_SEAT_CLASS = "MinSeatClass";
        String MAX_PRICE = "MaxPrice";
    }

    public MessageSystem getMessageSystem();

    public Collection<Ticket> search(Map<String, String> params);

    public boolean buy(Ticket ticket, User passenger);

    public void itemsFound(long requestId, Collection<SingleTicket> tickets);

    public void itemBought(long requestId, boolean result);
}
