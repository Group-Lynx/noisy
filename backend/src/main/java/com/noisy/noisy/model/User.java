package com.noisy.noisy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class User {
    @Id
    @Column(unique = true,nullable = true)
    private String name;
    @Column(nullable = false)
    private String pswd;

    public User(String name, String pswd) {
        this.name=name;
        this.pswd=pswd;
    }

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(pswd, user.pswd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pswd);
    }

    public String getName(){return this.name;}
    public String getPasswd(){return this.pswd;}

    public void setName(String name){this.name=name;}
    public void setPasswd(String passwd){this.pswd=passwd;}
}
