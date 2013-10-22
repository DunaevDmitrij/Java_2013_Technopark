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
public class MessageSystem implements MessageSystemI {
    private final Map<Address, ConcurrentLinkedQueue<Msg>> messages = new HashMap<>();
    private final AddressService addressService = new AddressService();

    @Override
    public void addService(Abonent abonent){
        this.messages.put(abonent.getAddress(), new ConcurrentLinkedQueue<Msg>());
    }

    //TODO: what to do if message.getTo is null or is wrong
    //TODO: make test on situation if message.getTo is null or wrong
    @Override
    public void sendMessage(Msg message){
        Queue<Msg> messageQueue = this.messages.get(message.getTo());
        messageQueue.add(message);
    }

    @Override
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

    @Override
    public AddressService getAddressService(){
        return this.addressService;
    }
}
