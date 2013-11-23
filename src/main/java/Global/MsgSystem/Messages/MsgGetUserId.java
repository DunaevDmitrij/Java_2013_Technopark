package Global.MsgSystem.Messages;

import Global.AccountService;
import Global.Address;
import Global.Imps.Frontend;
import Global.SessionService;

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
        Long userId = accountService.getUserIdByUserName(this.name);
        accountService.getMessageSystem().sendMessage(
                new MsgUpdateUserId(this.getTo(), this.getFrom(), userId, this.sessionId));
    }
}

