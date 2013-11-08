package Global;

import Global.Imps.UserSession;
import Global.MsgSystem.Abonent;
import Global.MsgSystem.Address;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 08.11.13
 * Time: 22:10
 */
public interface SessionService extends Abonent, Runnable {

    @Override
    Address getAddress();

    @Override
    void run();

    void createUserSession(Long sessionId, String userName);
    void closeSession(Long sessionId);

    Long getNewSessionId();
    UserSession getUserInfo(Long sessionId);
    void updateUserId(Long sessionId, Long userId);
}
