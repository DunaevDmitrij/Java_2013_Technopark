package Global.ResSystem;

import Global.Address;
import Global.MessageSystem;
import Global.ResourceSystem;
import Global.ResSystem.ReflectionHelper;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 23.11.13
 * Time: 8:44
 * Singleton.
 */

public class ResourceSystemImp implements ResourceSystem {
    private static ResourceSystemImp Instance;
    // private static final String RES_DIR = "/resources/";
    private final Address address;
    private final MessageSystem ms;

    /**
     * Singleton реализация.
     * @param ms ссылка на систему сообщений
     * @return возвращает ссылку на объект ресурсной системы
     */
    public static ResourceSystem getInstance(MessageSystem ms) {
        if (Instance == null) {
            Instance = new ResourceSystemImp(ms);
        }
        return Instance;
    }

    public static ResourceSystem getInstance() {
        return Instance;
    }

    private ResourceSystemImp(MessageSystem ms) {
        super();
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
        ms.getAddressService().setResSystem(this.address);
    }


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
}