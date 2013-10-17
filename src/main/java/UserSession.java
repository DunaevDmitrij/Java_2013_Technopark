/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:18
 */
public class UserSession
{
    private final String name;
    private final Long sessionId;
    private Long userId;

    public UserSession(Long sessionId, String name) {
        super();
        this.sessionId = sessionId;
        this.name = name;
        this.userId = -2L; //FIXME: const
    }

    public Long getSessionId(){
        return this.sessionId;
    }

    public String getName(){
        return this.name;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
