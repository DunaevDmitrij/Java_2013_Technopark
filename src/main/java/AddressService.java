/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 12.10.13
 * Time: 10:16
 * To change this template use File | Settings | File Templates.
 */

//класс, отвечающий за получение адресов всех основных сервисов
public class AddressService {
    private Address accountService;

    //получение адреса сервиса аккаунтов (AccountService)
    public Address getAccountService() {
        return accountService;
    }

    public void setAccountService(Address accountService) {
        this.accountService = accountService;
    }
}