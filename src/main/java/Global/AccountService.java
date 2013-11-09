package Global;

import Global.MsgSystem.Abonent;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 08.11.13
 * Time: 22:02
 */

public interface AccountService extends Abonent, Runnable {
    Long USER_NOT_EXIST = -1L;

    @Override
    Address getAddress();

    @Override
    void run();

    Long getUserIdByUserName(String userName);
    MessageSystem getMessageSystem();
}
