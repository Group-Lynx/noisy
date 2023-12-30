package com.noisy.noisy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.util.Objects;

@Entity
@IdClass(UserLastReadMsgKey.class)
public class UserLastReadMsg {
    @Id
    @Column(nullable = false)
    private String user_name;
    @Id
    @Column(nullable = false)
    private String chatroom_name;
    @Id
    @Column(nullable = false)
    private Integer last_read_msg_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLastReadMsg that = (UserLastReadMsg) o;
        return Objects.equals(user_name, that.user_name) && Objects.equals(chatroom_name, that.chatroom_name) && Objects.equals(last_read_msg_id, that.last_read_msg_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_name, chatroom_name, last_read_msg_id);
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getChatroom_name() {
        return chatroom_name;
    }

    public void setChatroom_name(String chatroom_name) {
        this.chatroom_name = chatroom_name;
    }

    public Integer getLast_read_msg_id() {
        return last_read_msg_id;
    }

    public void setLast_read_msg_id(Integer last_read_msg_id) {
        this.last_read_msg_id = last_read_msg_id;
    }
}
