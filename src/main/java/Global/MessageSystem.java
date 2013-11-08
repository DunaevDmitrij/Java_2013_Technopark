package Global;

import Global.MsgSystem.Messages.Msg;
import Global.MsgSystem.Abonent;
import Global.MsgSystem.AddressService;
/**
 * Author: artemlobachev
 * Date: 19.10.13
 */
@SuppressWarnings("UnusedDeclaration")//Created for tests and will be used in tests.
public interface MessageSystem {
    void addService(Abonent abonent);

    boolean sendMessage(Msg message);

    void execForAbonent(Abonent abonent);

    AddressService getAddressService();
}
