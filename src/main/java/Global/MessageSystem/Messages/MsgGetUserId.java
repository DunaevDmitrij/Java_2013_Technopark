package Global.MessageSystem.Messages;

import Global.MessageSystem.AccountService;
import Global.MessageSystem.Address;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:26
 */
public class MsgGetUserId extends MsgToAS {
    private final String name;
    private final Long sessionId;

    public MsgGetUserId(Address from, Address to, String name, Long sessionId) {
        super(from, to);
        this.name = name;
        this.sessionId = sessionId;
    }

    @Override
    void exec(AccountService accountService) {
        Long id = accountService.getUserIdByUserName(this.name);
        accountService.getMessageSystem().sendMessage(new MsgUpdateUserId(this.getTo(), this.getFrom(), this.sessionId, id));
    }
}

