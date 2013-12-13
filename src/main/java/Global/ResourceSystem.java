package Global;

import Global.MsgSystem.Abonent;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 30.11.13
 * Time: 17:17
 */

public interface ResourceSystem extends Abonent, Runnable {
    String RES_DIR = "resources";

    String PARAMS = "params.xml";
    String STAFF_INFO = "staff.xml";

    <ValueType>
    ValueType getParam(String name);
}
