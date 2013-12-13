package Global.ResSystem;

import Global.Address;
import Global.MessageSystem;
import Global.ResourceSystem;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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

    private Map<String, SysParam<?>> params;
    private SAXParserFactory factory;

    public ResourceSystemImp(MessageSystem ms) {
        super();
        this.ms = ms;
        this.address = new Address();
        ms.addService(this);
        ms.getAddressService().setResSystem(this.address);

        this.factory = SAXParserFactory.newInstance();
        this.params = this.loadResource(PARAMS, "SysParam");
    }

    private <ContentType extends XML_Convertable>
    Map<String, ContentType> loadResource(String xmlFile, String className) {
        try {
            SAXParser parser = this.factory.newSAXParser();
            SAX_Handler<ContentType> handler = new SAX_Handler<>(className);
            parser.parse(RES_DIR + File.separator + xmlFile, handler);

            return handler.getData();
        } catch (Exception e) {
            System.out.println("Ошибка при загрузке файла: " + xmlFile);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <ValueType>
    ValueType getParam(String name) {
        try {
            return (ValueType) params.get(name).getValue();
        } catch(Exception e) {
            System.out.println("Ошибка типа при выборке системного параметра: " + name);
            e.printStackTrace();
        }
        return null;
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