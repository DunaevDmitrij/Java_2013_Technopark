select * from
(
	select idFlight, FlightName, PlaneName, FlightTime, TimeDeparture, a1.Name as AirportArrival, a2.Name as AirportDeparture, placeconfig.Class as PlaceClass from placeconfig,
    (
		select idFlight, flight.Name as FlightName, plane.Name as PlaneName, FlightTime, TimeDeparture, AirportArrival, AirportDeparture from flight
		inner join plane on plane.idPlane = flight.Plane
		where idFlight in
		(
			select idFlight  from flight
			where AirportArrival = (select idAirport from airport where Name = '${AirportArrival}')
			and AirportDeparture = (select idAirport from airport where Name = '${AirportDeparture}')
			and TimeDeparture >= '${TimeDeparture_since}' and TimeDeparture <= '${TimeDeparture_to}'
		)
	) as t
	inner join Airport as a1 on a1.idAirport = t.AirportArrival
	inner join Airport as a2 on a2.idAirport = t.AirportDeparture
) as R
inner join (select Flight, Class, Price from flightcost) as fc on fc.Flight = R.idFlight and fc.class = R.PlaceClass
${additional}
