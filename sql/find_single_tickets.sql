select R.FlightName, Price, R.FlightTime, R.PlaceClass, R.TimeDeparture, plane.Name as PlaneName, a1.Name as AirportArrival, a2.Name as AirportDeparture from
(
select F.idFlight, F.FlightName, F.Plane, F.FlightTime, F.PlaceClass, F.TimeDeparture, F.AirportArrival, F.AirportDeparture, F.DefaultPlaceAmount - Buy.BuyPlaceAmount as ResutlPlace from 
	(
		select idFlight, FlightName, Plane, FlightTime, TimeDeparture, AirportArrival, AirportDeparture, Class as PlaceClass, Count as DefaultPlaceAmount from placeconfig, (
		select idFlight, flight.Name as FlightName, Plane, PlaneModel, FlightTime, TimeDeparture, AirportArrival, AirportDeparture from flight
		inner join plane on plane.idPlane = flight.Plane
		where idFlight in (
        select idFlight  from flight
		where AirportArrival = (select idAirport from airport where Name = '${AirportArrival}')
		and AirportDeparture = (select idAirport from airport where Name = '${AirportDeparture}')
		and TimeDeparture >= '${TimeDeparture_since}' and TimeDeparture <= '${TimeDeparture_to}'
        )) as t
		where t.PlaneModel = placeconfig.PlaneModel
	) as F,
	(
		select idFlight, PlaceClass, COUNT(*) as BuyPlaceAmount from ticketinfo
		inner join (
        select idFlight from flight
		where AirportArrival = (select idAirport from airport where Name = '${AirportArrival}')
		and AirportDeparture = (select idAirport from airport where Name = '${AirportDeparture}')
		and TimeDeparture >= '${TimeDeparture_since}' and TimeDeparture <= '${TimeDeparture_to}'
        ) as t on ticketinfo.Flight = t.idFlight
		group by idFlight, PlaceClass
	) as Buy
	where F.idFlight = Buy.idFlight and F.PlaceClass = Buy.PlaceClass
) as R
inner join plane on R.Plane = plane.idPlane 
inner join airport as a1 on R.AirportArrival = a1.idAirport
inner join airport as a2 on R.AirportDeparture = a2.idAirport
inner join flightcost on flightcost.Flight = R.idFlight and flightcost.class = R.PlaceClass
where R.ResutlPlace > 0;