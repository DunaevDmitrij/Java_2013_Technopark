package Global;

import Global.Imps.UserSession;
import Global.MsgSystem.Abonent;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 08.11.13
 * Time: 22:10
 */

public interface SessionService {
    void createUserSession(Long sessionId, String userName);
    void closeSession(Long sessionId);

    Long getNewSessionId();
    UserSession getUserInfo(Long sessionId);
    void updateUserId(Long sessionId, Long userId);
}
