package Global.ResSystem;

/**
 * Created with IntelliJ IDEA.
 * User: Kislenko Maksim
 * Date: 02.12.13
 * Time: 13:47
 */

public class Staff implements XML_Convertable {
    private String login;          // unique, corresponds to User.login in DB
    private String fullName;
    enum Role { ADMIN };
    private Role role;

    public Staff(String login, String fullName, Role role) {
        this.login = login;
        this.fullName = fullName;
        this.role = role;
    }

    @Override
    public String getUnique() {
        return this.login;
    }

    public String getFullName() {
        return this.fullName;
    }

    public Role getRole() {
        return this.role;
    }

    @Override
    public String toXML() {
        return "";
    }

    @Override
    public XML_Convertable fromXML() {
        return this;
    }
}
