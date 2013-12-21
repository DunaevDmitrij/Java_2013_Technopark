
select a1.Name as AirportArrival, a2.Name as AirportDeparture, TimeDeparture, PlaceClass, FlightTime, flight.Name as FlightName,
Lots.Ticket as idTicket, Price, plane.Name as PlaneName, Lots.EndDate, lothistory.StartDate, lothistory.CurrentPrice
from ticketinfo
inner join
(
	select Ticket, max(Type) as ResultType, EndDate from lothistory group by Ticket having ResultType < 3
) as Lots on Lots.Ticket = ticketinfo.Ticket
inner join flight on flight.idFlight = ticketinfo.Flight
inner join airport as a1 on a1.idAirport = AirportArrival
inner join airport as a2 on a2.idAirport = AirportDeparture
inner join flightcost on flightcost.Flight = flight.idFlight and flightcost.Class = ticketinfo.PlaceClass
inner join plane on plane.idPlane = flight.Plane
inner join lothistory on Lots.Ticket = lothistory.Ticket and lothistory.Type = 1

where a1.Name = '${AirportArrival}' and a2.Name = '${AirportDeparture}' and TimeDeparture >= '${TimeDeparture_since}' and TimeDeparture <= '${TimeDeparture_to}'
${additional}