package Global.Imps;

import Global.MessageSystem;
import Global.Address;
import Global.MsgSystem.Messages.MsgGetUserId;
import Global.SessionService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

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
public class SessionServiceImp implements SessionService {

    private final MessageSystem ms;
    private final Address address;
    // Счетчик для раздачи идентификаторов новым сессиям.
    private final AtomicLong sessionIdCounter = new AtomicLong();
    // Контейнер - отображение sessionId на UserSession.
    private final Map<Long, UserSession> sessionIdToUserSession = new HashMap<>();

    public SessionServiceImp(MessageSystem ms) {
        super();

        this.ms = ms;
        this.address = new Address();
        //регистрируем в MsgSystem
        ms.addService(this);
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    /**
     * Обновление идентификатора пользователя в конкретной UserSession.
     * @param sessionId идентификатор сессии нужного пользователя
     * @param userId новый идентификатор пользователя
     */
    @Override
    public synchronized void updateUserId(Long sessionId, Long userId) {
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
    @Override
    public Long getNewSessionId() {
        // Одновременно инкремент
        return this.sessionIdCounter.getAndIncrement();
    }

    // Получить идентификатор пользователя по сессии
    @Override
    public UserSession getUserInfo(Long sessionId) {
        return this.sessionIdToUserSession.get(sessionId);
    }

    // Закрытие сессии по её идентификатору
    @Override
    public void closeSession(Long sessionId) {
        this.sessionIdToUserSession.remove(sessionId);
    }

    /**
     * Создание новой пользовательской сессии. Вызывается при пройденной авторизации.
     * @param sessionId идентификатор сессии.
     * @param userName новое имя пользователя.
     */
    @Override
    public void createUserSession(Long sessionId, String userName) {
        UserSession userSession = new UserSession(sessionId, userName);
        //добавляем в sessionIdToUserSession
        this.sessionIdToUserSession.put(sessionId, userSession);

        // Отправляем сообщение к AccountServiceImp.
        Address accountServiceAddress = this.ms.getAddressService().getAccountService();
        Address serviceAddress = this.address;
        this.ms.sendMessage(new MsgGetUserId(serviceAddress, accountServiceAddress, userName, sessionId, this));
    }
}
