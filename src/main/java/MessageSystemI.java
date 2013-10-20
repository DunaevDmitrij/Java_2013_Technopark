/**
 * Author: artemlobachev
 * Date: 19.10.13
 */
public interface MessageSystemI {
    void addService(Abonent abonent);

    void sendMessage(Msg message);

    void execForAbonent(Abonent abonent);

    AddressService getAddressService();
}
