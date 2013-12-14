select R.FlightName, R.FlightTime, R.PlaceClass, R.TimeDeparture, R.ResutlPlace, plane.Name as PlaneName from
(
select Free.idFlight, Free.FlightName, Free.Plane, Free.FlightTime, Free.PlaceClass, Free.TimeDeparture, Free.DefaultPlaceAmount - Buy.BuyPlaceAmount as ResutlPlace from 
	(
		select idFlight, FlightName, Plane, FlightTime, TimeDeparture, Class as PlaceClass, Count as DefaultPlaceAmount from placeconfig, (
		select idFlight, flight.Name as FlightName, Plane, PlaneModel, FlightTime, TimeDeparture from flight
		inner join plane on plane.idPlane = flight.Plane
		where idFlight in (
        select idFlight from flight
		where AirportArrival = (select idAirport from airport where Name = '${AirportArrival}')
		and AirportDeparture = (select idAirport from airport where Name = '${AirportDeparture}')
		and TimeDeparture >= '2013' and TimeDeparture <= '2014'
        )) as t
		where t.PlaneModel = placeconfig.PlaneModel
	) as Free,
	(
		select idFlight, PlaceClass, COUNT(*) as BuyPlaceAmount from ticketinfo
		inner join (
        select idFlight from flight
		where AirportArrival = (select idAirport from airport where Name = '${AirportArrival}')
		and AirportDeparture = (select idAirport from airport where Name = '${AirportDeparture}')
		and TimeDeparture >= '2013' and TimeDeparture <= '2014'
        ) as t on ticketinfo.Flight = t.idFlight
		group by idFlight, PlaceClass
	) as Buy
	where Free.idFlight = Buy.idFlight and Free.PlaceClass = Buy.PlaceClass
) as R, plane where R.Plane = plane.idPlane;