package Global;

import Global.MsgSystem.Abonent;
import Global.ResSystem.XML_Convertable;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 30.11.13
 * Time: 17:17
 */

public interface ResourceSystem extends Abonent, Runnable {
    String RES_DIR = "resources";

    String PARAMS = "params.xml";

    <ValueType>
    ValueType getParam(String name);

    <ContentType extends XML_Convertable>
    ContentType getRecord(String xmlFile, String uniqueValue);
}
