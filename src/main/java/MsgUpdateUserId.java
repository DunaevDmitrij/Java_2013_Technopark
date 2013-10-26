/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 11:06
 */
public class MsgUpdateUserId extends MsgToSS {

    private final Long sessionId;
    private final Long id;

    public MsgUpdateUserId(Address from, Address to, Long sessionId, Long id) {
        super(from, to);
        this.sessionId = sessionId;
        this.id = id;
    }

    @Override
    void exec(SessionService sessionService) {
        sessionService.updateUserId(this.sessionId, this.id);
    }

}
