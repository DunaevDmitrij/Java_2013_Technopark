insert into ticket(Passenger, BuyTime) values ((select idUser from user where Login = '${UserLogin}'), NOW());