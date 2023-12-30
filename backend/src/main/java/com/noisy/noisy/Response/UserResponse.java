package com.noisy.noisy.Response;

public class UserResponse {
    private String name;

    public UserResponse(String user_name){
        this.name=user_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
