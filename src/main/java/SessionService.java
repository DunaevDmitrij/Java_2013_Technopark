import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 19.10.13
 * Time: 9:48
 * To change this template use File | Settings | File Templates.
 */
public class SessionService {
    private Address frontendAddress;
    private MessageSystem ms;
    private final AtomicLong sessionIdCounter = new AtomicLong();
    private final Map<Long, UserSession> sessionIdToUserSession = new HashMap<>();

    public SessionService(MessageSystem ms, Address address) {
        this.frontendAddress = address;
        this.ms = ms;
    }

    public void updateUserId(Long sessionId, Long userId) {
        UserSession userSession = this.sessionIdToUserSession.get(sessionId);
        if (userSession == null) {
            System.out.append("Can't find user session for: ").append(sessionId.toString());
            return;
        }
        userSession.setUserId(userId);
        userSession.setComplete(); //процесс получения userId завершен
    }

    public Long getSessionId() {
        return this.sessionIdCounter.getAndIncrement();
    }

    public UserSession getUserInfo(Long sessionId) {
        return this.sessionIdToUserSession.get(sessionId);
    }

    public void closeSession(Long sessionId) {
        this.sessionIdToUserSession.remove(sessionId);
    }

    public void createUserSession(Long sessionId, String userName) {
        UserSession userSession = new UserSession(sessionId, userName);
        //добавляем в sessionIdToUserSession
        this.sessionIdToUserSession.put(sessionId, userSession);

        Address serviceAddress = this.frontendAddress;
        Address accountServiceAddress = this.ms.getAddressService().getAccountService();

        this.ms.sendMessage(new MsgGetUserId(serviceAddress, accountServiceAddress, userName, sessionId));
    }
}
