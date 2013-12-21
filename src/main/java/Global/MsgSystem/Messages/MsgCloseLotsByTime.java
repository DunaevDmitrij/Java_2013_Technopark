package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;

/**
 * Author: artemlobachev
 * Date: 16.12.13
 */
public class MsgCloseLotsByTime extends MsgToDB{

    public MsgCloseLotsByTime(Address from, Address to){
        super(from, to);
    }

    @Override
    void exec(DBService dbService) {
        dbService.closeLotsByTime();
    }
}
