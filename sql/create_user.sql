INSERT INTO `User` (`Login`, `Password`, `FirstName`, `LastName`, `PassportNumber`)
VALUES ('${login}', md5('${password}'), '${firstName}', '${lastName}', '${passportInfo}');