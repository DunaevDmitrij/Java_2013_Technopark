select FreePlaces - BusyPlaces as Result from
(
select Count as FreePlaces from placeconfig
where Class = '${Class}' and PlaneModel =
(select PlaneModel from plane where idPlane = (select Plane from flight where Name = '${FlightName}'))
) as F,
(select count(*) as BusyPlaces from ticketinfo
where Flight = (select idFlight from flight where Name = '${FlightName}')
and PlaceClass = '${Class}'
) as B;