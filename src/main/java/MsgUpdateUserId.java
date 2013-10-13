/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */
public class MsgUpdateUserId extends MsgToFrontend {

    private Long sessionId;
    private Long id;

    public MsgUpdateUserId(Address from, Address to, Long sessionId, Long id) {
        super(from, to);
        this.sessionId = sessionId;
        this.id = id;
    }

    @Override
    void exec(Frontend frontend) {
        frontend.setId(sessionId, id);
    }

}
