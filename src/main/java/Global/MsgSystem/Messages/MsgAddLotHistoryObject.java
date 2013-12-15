package Global.MsgSystem.Messages;

import Global.Address;
import Global.DBService;
import Global.Lot;
import Global.LotHistoryObject;

/**
 * Author: artemlobachev
 * Date: 15.12.13
 */
public class MsgAddLotHistoryObject extends MsgToDB {
    private final Lot lot;
    private final LotHistoryObject object;

    public MsgAddLotHistoryObject(Address from, Address to, Lot lot, LotHistoryObject object){
        super(from, to);
        this.lot = lot;
        this.object = object;

    }
    @Override
    void exec(DBService dbService) {
        //FIXME: удалить имплементацию!
        //dbService.addLotHistroryObject(this.lot, this.object);
    }
}
