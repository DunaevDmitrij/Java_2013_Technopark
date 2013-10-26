import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 19.10.13
 * Time: 9:48
 *
 */

/**
 * Абстракция - контейнер для UserSession. Хранит, создает и делает выборку.
 */
public class SessionService implements Abonent, Runnable {

    private final MessageSystem ms;
    private final Address address;
    // Счетчик для раздачи идентификаторов новым сессиям.
    private final AtomicLong sessionIdCounter = new AtomicLong();
    // Контейнер - отображение sessionId на UserSession.
    private final Map<Long, UserSession> sessionIdToUserSession = new HashMap<>();

    public SessionService(MessageSystem ms) {
        super();

        this.ms = ms;
        this.address= new Address();
        //регистрируем в MessageSystem
        ms.addService(this);
        //регестрируем в AddressService, чтобы каждый мог обратиться к SessionSerivice
        ms.getAddressService().setAccountService(this.address);
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public void run() {
        while (true) {
            this.ms.execForAbonent(this);

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Обновление идентификатора пользователя в конкретной UserSession.
     * @param sessionId идентификатор сессии нужного пользователя
     * @param userId новый идентификатор пользователя
     */
    public void updateUserId(Long sessionId, Long userId) {

        UserSession userSession = this.sessionIdToUserSession.get(sessionId);
        if (userSession == null) {
            // Обработка неожиданности
            System.out.append("Can't find user session for: ").append(sessionId.toString());
            return;
        }
        userSession.setUserId(userId);
        userSession.setComplete(); //процесс получения userId завершен
    }

    // Получить идентификатор для новой сессии.
    public Long getNewSessionId() {
        // Одновременно инкремент
        return this.sessionIdCounter.getAndIncrement();
    }

    // Получить идентификатор пользователя по сессии
    public UserSession getUserInfo(Long sessionId) {
        return this.sessionIdToUserSession.get(sessionId);
    }

    // Закрытие сессии по её идентификатору
    public void closeSession(Long sessionId) {
        this.sessionIdToUserSession.remove(sessionId);
    }

    /**
     * Создание новой пользовательской сессии. Вызывается при пройденной авторизации.
     * @param sessionId идентификатор сессии.
     * @param userName новое имя пользователя.
     */
    public void createUserSession(Long sessionId, String userName) {

        UserSession userSession = new UserSession(sessionId, userName);
        //добавляем в sessionIdToUserSession
        this.sessionIdToUserSession.put(sessionId, userSession);

        // Отправляем сообщение к AccountService.
        Address accountServiceAddress = this.ms.getAddressService().getAccountService();
        Address serviceAddress = this.address;
        this.ms.sendMessage(new MsgGetUserId(serviceAddress, accountServiceAddress, userName, sessionId));
    }
}
