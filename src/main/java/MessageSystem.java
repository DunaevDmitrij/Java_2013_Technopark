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
public class MessageSystem {
    private final Map<Address, ConcurrentLinkedQueue<Msg>> messages = new HashMap<>();
    private final AddressService addressService = new AddressService();

    public void addService(Abonent abonent){
        this.messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Msg>());
    }

    public void sendMessage(Msg message){
        Queue<Msg> messageQueue = this.messages.get(message.getTo());
        messageQueue.add(message);
    }

    public void execForAbonent(Abonent abonent) {
        Queue<Msg> messageQueue = this.messages.get(abonent.getAddress());
        if(messageQueue == null){
            return;
        }
        while(!messageQueue.isEmpty()){
            Msg message = messageQueue.poll();
            message.exec(abonent);
        }
    }

    public AddressService getAddressService(){
        return this.addressService;
    }
}
