package edu.uta.cse.personaltutorservice.Request_Objects;

import edu.uta.cse.personaltutorservice.Model_Objects.User;

/**
 * Created by Arun on 2/22/2016.
 */
public class UserRequest {
    private edu.uta.cse.personaltutorservice.Model_Objects.User User;

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        this.User = user;
    }
}
