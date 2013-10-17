import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 11.10.13
 * Time: 20:21
 */
public class AccountService implements Abonent, Runnable {

    private final Address address;
    private final Map<String, Long> userNameToUserId;
    private final MessageSystem ms;

    public AccountService(MessageSystem ms) {
        super();
        this.ms = ms;
        this.address = new Address();
        //регистрируем AccountService в MessageSystem
        ms.addService(this);
        //регестрируем AccountService в AddressService, что бы каждый мог обратиться к AccountSerivice
        ms.getAddressService().setAccountService(this.address);

        //добавляем пользователей
        //TODO: брать из базы
        this.userNameToUserId = new HashMap<>();
        this.userNameToUserId.put("vasia", 0L);
        this.userNameToUserId.put("valera", 1L);
    }

    //имплементация интерфейса Abonent
    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public void run(){
        while(true){
            this.ms.execForAbonent(this);

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  Получение UserID по User Name
     * @param userName Имя пользователя
     * @return UserID
     */

    public Long getUserIdByUserName(String userName)
    {
        //имитация поиска
        try {
            sleep(5000); //на данный момент заглушка
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //FIXME: -1L заменить на константу
        //возвращаем userId, если найден
        if (this.userNameToUserId.containsKey(userName))
        {
            return this.userNameToUserId.get(userName);
        }
        {
            return -1L;
        }
    }

    public MessageSystem getMessageSystem(){
        return this.ms;
    }
}
