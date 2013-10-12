/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:18
 * To change this template use File | Settings | File Templates.
 */
public class UserSession
{
    private String name;
    private Long sessionId;
    private Long userId;

    public UserSession(Long sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
        this.userId = -2L; //FIXME: const
    }

    public String getName(){
        return name;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
