/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 11:06
 */
public class MsgUpdateUserId extends MsgToFrontend {

    private final Long sessionId;
    private final Long id;

    public MsgUpdateUserId(Address from, Address to, Long sessionId, Long id) {
        super(from, to);
        this.sessionId = sessionId;
        this.id = id;
    }

    @Override
    void exec(Frontend frontend) {
        frontend.setId(this.sessionId, this.id);
    }

}
