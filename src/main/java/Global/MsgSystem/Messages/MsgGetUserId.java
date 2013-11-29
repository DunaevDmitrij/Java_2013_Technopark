package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:26
 */
public class MsgGetUserId extends MsgToDB {
    private final String name;
    private final Long sessionId;

    public MsgGetUserId(Address from, Address to, String name, Long sessionId) {
        super(from, to);
        this.name = name;
        this.sessionId = sessionId;
    }

    @Override
    void exec(DBService dbService) {
        Long id = dbService.getUserIdByUserName(this.name, "root");    //FIXME:
        dbService.getMessageSystem().sendMessage(new MsgUpdateUserId(this.getTo(), this.getFrom(), this.sessionId, id));
    }
}

