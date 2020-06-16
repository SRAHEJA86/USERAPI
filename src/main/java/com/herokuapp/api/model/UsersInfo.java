package com.herokuapp.api.model;

import java.util.List;

public class UsersInfo {
    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }
    /** List of users **/
    List<User> usersList;
}
