package Global.MsgSystem.Messages;

import Global.Address;
import Global.Imps.Frontend;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 23.11.13
 * Time: 11:45
 */

public class MsgUpdateUserId extends MsgToFrontend {
    private final Long userId;
    private final Long sessionId;

    public MsgUpdateUserId(Address from, Address to, Long sessionId, Long userId) {
        super(from, to);
        this.userId = userId;
        this.sessionId = sessionId;
    }

    @Override
    void exec(Frontend frontend) {
        frontend.updateUserID(this.sessionId, this.userId);
    }
}
