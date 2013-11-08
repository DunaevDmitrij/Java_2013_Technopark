package Global.MsgSystem;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:16
 */
//класс, отвечающий за получение адресов всех основных сервисов
public class AddressService {
    private Address accountService;
    private Address frontend;
    private Address sessionService;

    @SuppressWarnings("UnusedDeclaration")
    public Address getFrontend() {
        return this.frontend;
    }

    public void setFrontend(Address frontend) {
        this.frontend = frontend;
    }

    //получение адреса сервиса аккаунтов (AccountServiceImp)
    public Address getAccountService() {
        return this.accountService;
    }

    public void setAccountService(Address accountService) {
        this.accountService = accountService;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Address getSessionService() {
        return this.sessionService;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setSessionService(Address sessionService) {
        this.sessionService = sessionService;
    }
}