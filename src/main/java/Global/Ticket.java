package Global;

import Global.mechanics.SingleTicket;

import java.util.Date;
import java.util.List;

/**
 * Author: artemlobachev
 * Date: 28.11.13
 */
public interface Ticket {
    public static enum seatClass{
            SEAT_CLASS_FIRST, SEAT_CLASS_BUSINESS, SEAT_CLASS_ECONOMIC
    }

    public String getOwner();
    public String getDepartureAirport();
    public Date getDepartureDateTime();
    public String getArrivalAirport();
    public long getFlightTime();
    public List<SingleTicket> getRoute();   //TODO make class Route or return Formatted String?
    public seatClass getMinSeatClass();
    public int getPrice();
    public boolean isTemporary();
}
