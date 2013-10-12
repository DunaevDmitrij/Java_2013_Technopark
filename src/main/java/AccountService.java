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
public class AccountService {

    private Map<String, Long> userNameToUserId;

    /**
     * FIXME: добавить описание :)
     * @param
     * @param
     * @return
     */

    public AccountService()
    {
        userNameToUserId = new HashMap<>();
        userNameToUserId.put("vasia", 0L);
        userNameToUserId.put("valera", 1L);
    }

    public Long getUserIdByUserName(String userName) throws InterruptedException
    {
        //имитация поиска
        //sleep(5000);

        //FIXME: -1L заменить на константу
        //возвращаем userId, если найден
        if (userNameToUserId.containsKey(userName))
            return userNameToUserId.get(userName);
        else
            return -1L;
    }
}
