package Global.MsgSystem;

import Global.AccountService;
import Global.Address;
import Global.Imps.AccountServiceImp;
import Global.MessageSystem;
import Global.MsgSystem.Messages.Msg;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:14
 */
public class MessageSystemImp implements MessageSystem {
    private final Map<Address, ConcurrentLinkedQueue<Msg>> messages = new HashMap<>();
    private final AddressService addressService = new AddressService();

    @Override
    public void addService(Abonent abonent){
        this.messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Msg>());
    }

    @Override
    public boolean sendMessage(Msg message){
        boolean result;
        Queue<Msg> messageQueue = this.messages.get(message.getTo());
        try {
            messageQueue.add(message);
            result = true;
        } catch (Exception e) {
           result = false;
        }
        return result;
    }

    @Override
    public void execForAbonent(Abonent abonent) {
        Queue<Msg> messageQueue = this.messages.get(abonent.getAddress());

        while(!messageQueue.isEmpty()){
            Msg message = messageQueue.poll();
            message.exec(abonent);
        }
    }

    @Override
    public AddressService getAddressService(){
        return this.addressService;
    }
}
