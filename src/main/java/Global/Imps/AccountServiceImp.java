package Global.Imps;

import Global.AccountService;
import Global.MessageSystem;
import Global.Address;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 11.10.13
 * Time: 20:21
 */
public class AccountServiceImp implements AccountService {

    private final Address address;
    private final Map<String, Long> userNameToUserId;
    private final MessageSystem ms;
    private static final int WAIT_BEFORE_ANSWER = 5000;

    public AccountServiceImp(MessageSystem ms) {
        super();
        this.ms = ms;
        this.address = new Address();
        //регистрируем AccountServiceImp в MsgSystem
        ms.addService(this);
        //регестрируем AccountServiceImp в AddressService, что бы каждый мог обратиться к AccountSerivice
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
    public void run() {
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
     * @return UserID или USER_NOT_EXIST если пользователь не существует
     */

    @Override
    public Long getUserIdByUserName(String userName)
    {
        //имитация поиска
        try {
            sleep(WAIT_BEFORE_ANSWER); //на данный момент заглушка
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //возвращаем userId, если найден
        if (this.userNameToUserId.containsKey(userName))
        {
            return this.userNameToUserId.get(userName);
        }
        {
            return USER_NOT_EXIST;
        }
    }

    @Override
    public MessageSystem getMessageSystem() {
        return this.ms;
    }
}