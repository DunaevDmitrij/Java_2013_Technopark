package Global.MsgSystem.Messages;

import Global.Address;
import Global.MsgSystem.Abonent;

import java.util.Map;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class MsgFindTicket extends Msg {
    private Map<String, String> params;
    private long requsetId;

    public MsgFindTicket(Address from, Address to, Map<String, String> params, long requestId){
        super(from, to);
        this.params = params;
        this.requsetId = requestId;
    }

    @Override
    public void exec(Abonent abonent) {

    }
}
