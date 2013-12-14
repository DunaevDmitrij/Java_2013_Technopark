package Global.mechanics;

import Global.Ticket;

import java.util.Date;

/**
 * Author: artemlobachev
 * Date: 07.12.13
 */
public class SingleTicket {

    /*
    For existing ticket.
    Vadim, use this!
     */
    public SingleTicket(long id, String departureAirport, String arrivalAirport, Date departureTime, long flightTime, String flightNumber, Ticket.seatClass seatClass, String planeModel) {
        super();
        this.isTemporary = false;
        this.id = id;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.flightTime = flightTime;
        this.flightNumber = flightNumber;
        this.seatClass = seatClass;
        this.planeModel = planeModel;
    }
    /*
    For temporary tickets (in search).
     */
    public SingleTicket(String departureAirport, String arrivalAirport, Date departureTime, long flightTime, String flightNumber, Ticket.seatClass seatClass, String planeModel) {
        super();
        this.isTemporary = true;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.flightTime = flightTime;
        this.flightNumber = flightNumber;
        this.seatClass = seatClass;
        this.planeModel = planeModel;
    }

    //TODO: do we need to save SingleTickets or only big ticket?
    /*
    public void save(){
    }
    public void saved(){
        this.isTemporary = false;
    }
    */

    public String getPlaneModel() {
        return this.planeModel;
    }

    public String getDepartureAirport() {
        return this.departureAirport;
    }

    public String getArrivalAirport() {
        return this.arrivalAirport;
    }

    public Date getDepartureTime() {
        return this.departureTime;
    }

    public long getFlightTime() {
        return this.flightTime;
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }

    public Ticket.seatClass getSeatClass() {
        return this.seatClass;
    }

    public boolean isTemporary() {
        return this.isTemporary;
    }

    private long id;
    private String planeModel;
    private String departureAirport;
    private String arrivalAirport;
    private Date departureTime;
    private long flightTime;
    private String flightNumber;
    private Ticket.seatClass seatClass;
    private boolean isTemporary;

}
