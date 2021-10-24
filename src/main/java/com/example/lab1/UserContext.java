package com.example.lab1;

import com.example.lab1.model.User;

public class UserContext {
    private static volatile UserContext instance;

    public User value;

    private UserContext(User value) {
        this.value = value;
    }

    public static UserContext getInstance() {
        UserContext result = instance;
        if (result != null) {
            return result;
        }
        synchronized(UserContext.class) {
            if (instance == null) {
                instance = new UserContext(null);
            }
            return instance;
        }
    }
}
