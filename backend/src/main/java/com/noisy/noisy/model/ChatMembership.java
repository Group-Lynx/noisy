package com.noisy.noisy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(ChatMembershipkey.class)
public class ChatMembership {
//    @Id
//    @Column(unique = true,nullable = true)
//    private String chat_name;
//    @Id
//    @Column(unique = true,nullable = true)
//    private String owner_name;

    @Id
    @Column(nullable = true)
    private String chat_name;
    @Id
    @Column(nullable = true)
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
}
