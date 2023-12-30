package com.noisy.noisy.model;


import java.io.Serializable;
import java.util.Objects;

public class ChatMembershipkey implements Serializable {
    private String chat_name;

    private String user_name;

    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMembershipkey that = (ChatMembershipkey) o;
        return Objects.equals(chat_name, that.chat_name) && Objects.equals(user_name, that.user_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chat_name, user_name);
    }
}
