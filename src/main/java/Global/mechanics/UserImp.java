package Global.mechanics;

import Global.User;

/**
 * Author: artemlobachev
 * Date: 14.12.13
 */
public class UserImp implements User {
    private final String userName;

    public UserImp(String userName) {
        super();
        this.userName = userName;
    }


    @Override
    public String getUserName() {
        return this.userName;
    }
}
