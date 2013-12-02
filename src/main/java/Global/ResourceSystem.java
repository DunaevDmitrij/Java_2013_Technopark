package Global;

import Global.MsgSystem.Abonent;
import Global.ResSystem.Staff;
import Global.ResSystem.SysParam;
import Global.ResSystem.XML_Convertable;

/**
 * Created with IntelliJ IDEA.
 * User: max
 * Date: 30.11.13
 * Time: 17:17
 */

public interface ResourceSystem extends Abonent, Runnable {
    String RES_DIR = "/resources";

    String PARAMS = "params.xml";
    String STAFF_INFO = "staff.xml";

    void loadData();

    <ValueType>
    ValueType getParam(String name);

    Staff getStaffInfo(String login);
    void append(String fileName, XML_Convertable record);
}
