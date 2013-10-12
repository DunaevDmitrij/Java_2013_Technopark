import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 11.10.13
 * Time: 20:21
 * To change this template use File | Settings | File Templates.
 */
public class AccountService implements Abonent, Runnable {

    private Address address;
    private Map<String, Long> userNameToUserId;
    private MessageSystem ms;

    public AccountService(MessageSystem ms){
        this.ms = ms;
        this.address = new Address();
        //регистрируем AccountService в MessageSystem
        ms.addService(this);
        //регестрируем AccountService в AddressService, что бы каждый мог обратиться к AccountSerivice
        ms.getAddressService().setAccountService(address);

        //добавляем пользователей
        //TODO: брать из базы
        userNameToUserId = new HashMap<>();
        userNameToUserId.put("vasia", 0L);
        userNameToUserId.put("valera", 1L);
    }

    //имплементация интерфейса Abonent
    public Address getAddress() {
        return address;
    }

    public void run(){
        while(true){
            ms.execForAbonent(this);

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    /**
     * FIXME: добавить описание :)
     * @param
     * @param
     * @return
     */

    public Long getUserIdByUserName(String userName)
    {
        //имитация поиска
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        //FIXME: -1L заменить на константу
        //возвращаем userId, если найден
        if (userNameToUserId.containsKey(userName))
            return userNameToUserId.get(userName);
        else
            return -1L;
    }

    public MessageSystem getMessageSystem(){
        return ms;
    }
}
