package com.noisy.noisy.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class LoginSessionKey implements Serializable {
    private String user_name;
    private UUID token;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginSessionKey that = (LoginSessionKey) o;
        return Objects.equals(user_name, that.user_name) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_name, token);
    }
}
