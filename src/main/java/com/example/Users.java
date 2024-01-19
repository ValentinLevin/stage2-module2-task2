package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Users {
    private static final Users INSTANCE = new Users();

    private final List<String> users;

    private Users() {
        users = new ArrayList<>();
        users.add("admin");
        users.add("user");
    }

    public static Users getInstance() {
        return INSTANCE;
    }

    public List<String> getUsers() {
        return Collections.unmodifiableList(users);
    }
}
