package Global.ResSystem;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 02.12.13
 * Time: 13:21
 */

public interface XML_Convertable {
    String getUnique();
    String toXML();
    XML_Convertable fromXML();
}
