package Global;

import java.util.Date;

/**
 * Author: artemlobachev
 * Date: 28.11.13
 */
public interface Ticket {
    public static enum seatClass{
            SEAT_CLASS_FIRST, SEAT_CLASS_BUSINESS, SEAT_CLASS_ECONOMIC
    }

    public String getOwner();
    public String gerDepartureAirport();
    public Date getDepartureDateTime();
    public String getArrivalAirport();
    public long getFlightTime();
    public Object getRoute();   //TODO make class Route or return Formatted String?
    public seatClass getMinSeatClass();
}
