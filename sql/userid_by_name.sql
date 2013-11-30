SELECT `idUser`
FROM `User`
WHERE `Login` = ${login}
  AND `Password` = md5(${password})
LIMIT 1;