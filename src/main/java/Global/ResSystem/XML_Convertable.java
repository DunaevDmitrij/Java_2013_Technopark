package Global.ResSystem;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 02.12.13
 * Time: 13:21
 */

public interface XML_Convertable extends Serializable {
    long serialVersionUID = -3895203507200457732L;

    String getUnique();
    String getUniqueFields();
}
