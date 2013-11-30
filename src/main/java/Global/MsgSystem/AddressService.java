package Global.MsgSystem;

import Global.Address;

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
    private Address resSystem;


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

    public Address getResSystem() {
        return this.resSystem;
    }

    public void setResSystem(Address resSystem) {
        this.resSystem = resSystem;
    }
}