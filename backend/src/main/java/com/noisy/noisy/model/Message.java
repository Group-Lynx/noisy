package com.noisy.noisy.model;

import com.noisy.noisy.request.MessageReq;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true,nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String user_name;
    @Column(nullable = false)
    private String chat_name;
    @Column(nullable = false)
    private LocalDateTime sent_date;
    @Column(nullable = false)
    private String content;

    public Message(MessageReq msg, String chat_name) {
        this.user_name = msg.getSender();
        this.chat_name = chat_name;
        this.sent_date = LocalDateTime.now();
        this.content = msg.getContent();
    }

    public Message() {}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getChat_name() {
        return chat_name;
    }

    public void setChat_name(String chat_name) {
        this.chat_name = chat_name;
    }

    public LocalDateTime getSent_date() {
        return sent_date;
    }

    public void setSent_date(LocalDateTime sent_date) {
        this.sent_date = sent_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(user_name, message.user_name) && Objects.equals(chat_name, message.chat_name) && Objects.equals(sent_date, message.sent_date) && Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_name, chat_name, sent_date, content);
    }
}
