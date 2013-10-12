/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */

public class MsgGetUserId extends MsgToAS {
    private String name;
    private Long sessionId;

    public MsgGetUserId(Address from, Address to, String name, Long sessionId) {
        super(from, to);
        this.name = name;
        this.sessionId = sessionId;
    }

    void exec(AccountService accountService) {
        Long id = accountService.getUserIdByUserName(name);
        accountService.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), sessionId, id));
    }
}

