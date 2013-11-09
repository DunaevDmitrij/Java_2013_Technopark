package Global.MsgSystem.Messages;

import Global.AccountService;
import Global.Address;
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
    private final SessionService poster;

    public MsgGetUserId(Address from, Address to, String name, Long sessionId, SessionService callBack) {
        super(from, to);
        this.name = name;
        this.sessionId = sessionId;
        this.poster = callBack;
    }

    @Override
    void exec(AccountService accountService) {
        Long userId = accountService.getUserIdByUserName(this.name);
        this.poster.updateUserId(this.sessionId, userId);
    }
}

