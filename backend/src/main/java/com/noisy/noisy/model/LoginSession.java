package com.noisy.noisy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
@Entity
@IdClass(LoginSessionKey.class)
public class LoginSession {
    @Id
    @Column(nullable = false)
    private String user_name;
    @Id
    @Column(nullable = false)
    private UUID token;
    @Column(nullable = false)
    private LocalDateTime valid_until;

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

    public LocalDateTime getValid_until() {
        return valid_until;
    }

    public void setValid_until(LocalDateTime valid_until) {
        this.valid_until = valid_until;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginSession that = (LoginSession) o;
        return Objects.equals(user_name, that.user_name) && Objects.equals(token, that.token) && Objects.equals(valid_until, that.valid_until);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_name, token, valid_until);
    }
}
