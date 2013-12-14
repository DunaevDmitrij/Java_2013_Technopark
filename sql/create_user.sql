INSERT INTO `User` (`Login`, `Password`)
VALUES ('${login}', md5('${password}'));