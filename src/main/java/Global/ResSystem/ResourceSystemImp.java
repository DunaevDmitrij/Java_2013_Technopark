package Global.ResSystem;

import Global.Address;
import Global.MessageSystem;
import Global.ResourceSystem;

import java.io.File;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 23.11.13
 * Time: 8:44
 */

public class ResourceSystemImp implements ResourceSystem {
    private final Address address;
    private final MessageSystem ms;

    public ResourceSystemImp(MessageSystem ms) {
        super();
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
        ms.getAddressService().setResSystem(this.address);
    }

    public void loadData() {

    }

    @Override
    public <ValueType>
    ValueType getParam(String name) {
        return this.<SysParam<ValueType>> search(PARAMS, name).getValue();
    }

    @Override
    public Staff getStaffInfo(String login) {
        return this.search(PARAMS, login);
    }

    private <ResType extends XML_Convertable>
    ResType search(String fileName, String uniqueValue) {
        return null;
    }

    @Override
    public void append(String fileName, XML_Convertable record) {
        return;
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