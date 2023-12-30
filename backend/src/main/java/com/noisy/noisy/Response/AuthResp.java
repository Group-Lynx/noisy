package com.noisy.noisy.Response;

public class AuthResp {
    private String user;
    private String token;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthResp(String user, String token) {
        this.user = user;
        this.token = token;
    }
}
