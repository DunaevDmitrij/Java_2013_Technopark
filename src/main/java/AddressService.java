/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:16
 */
//класс, отвечающий за получение адресов всех основных сервисов
public class AddressService {
    private Address accountService;

    //получение адреса сервиса аккаунтов (AccountService)
    public Address getAccountService() {
        return this.accountService;
    }

    public void setAccountService(Address accountService) {
        this.accountService = accountService;
    }
}