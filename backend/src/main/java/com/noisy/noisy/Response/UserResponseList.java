package com.noisy.noisy.Response;

import java.util.List;

public class UserResponseList {
    private List<UserResponse> users;

    public List<UserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserResponse> users) {
        this.users = users;
    }

    public UserResponseList(List<UserResponse> users) {
        this.users = users;
    }
}
