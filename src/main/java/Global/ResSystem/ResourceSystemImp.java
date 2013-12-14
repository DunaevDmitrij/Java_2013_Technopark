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
    private Map<String, Map<String, ? extends XML_Convertable>> store;
    private final SAXParserFactory factory = SAXParserFactory.newInstance();

    public ResourceSystemImp() {
        super();
        this.store = new HashMap<>();
        this.<SysParam<?>> loadResource(PARAMS, "Global.ResSystem.SysParam");
    }

    protected <ContentType extends XML_Convertable>
    void loadResource(String xmlFile, String className) {
        try {
            SAXParser parser = this.factory.newSAXParser();
            SAX_Handler<ContentType> handler = new SAX_Handler<>(className);
            parser.parse(RES_DIR + File.separator + xmlFile, handler);

            this.store.put(xmlFile, handler.getData());

        } catch (Exception e) {
            System.out.println("Ошибка при загрузке файла: " + xmlFile);
            e.printStackTrace();
        }
    }

    @Override
    public <ValueType>
    ValueType getParam(String name) {
        SysParam<ValueType> param = (SysParam<ValueType>) this.store.get(PARAMS).get(name);
        return param.getValue();
    }

    @Override
    public <ContentType extends XML_Convertable>
    ContentType getRecord(String xmlFile, String uniqueValue) {
        return (ContentType) this.store.get(xmlFile).get(uniqueValue);
    }
}