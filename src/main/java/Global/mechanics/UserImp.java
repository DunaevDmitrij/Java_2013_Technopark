package Global.mechanics;

import Global.User;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class UserImp implements User {
    private final String userLogin;

    public UserImp(String userName) {
        super();
        this.userLogin = userName;
    }


    @Override
    public String getUserLogin() {
        return this.userLogin;
    }
}
