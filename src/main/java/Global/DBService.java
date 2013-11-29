package Global;

import Global.MsgSystem.Abonent;

/**
 * Created with IntelliJ IDEA.
 * User: MiST
 * Date: 23.11.13
 * Time: 12:15
 */

public interface DBService extends Abonent, Runnable {

    Long USER_NOT_EXIST = -1L;

    public MessageSystem getMessageSystem();

    //Получение ID пользователя по логину и паролю
    //Если логина нет в БД, либо пароль не верен, возвращается USER_NOT_EXIST
    Long getUserIdByUserName(String login, String password);
}
