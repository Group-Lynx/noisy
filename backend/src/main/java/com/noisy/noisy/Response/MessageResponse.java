package com.noisy.noisy.Response;

import com.noisy.noisy.model.Message;
import com.noisy.noisy.request.MessageReq;

import java.time.LocalDateTime;

public class MessageResponse {
    private String sender;
    private String content;
    private DateTimePack sent_date;

    public MessageResponse(String sender, String content, DateTimePack sent_date) {
        this.sender = sender;
        this.content = content;
        this.sent_date = sent_date;
    }

    public MessageResponse(Message msg) {
        this.sender = msg.getUser_name();
        this.content = msg.getContent();
        this.sent_date = new DateTimePack(msg.getSent_date());
    }

    public MessageResponse(MessageReq msg) {
        this.sender = msg.getSender();
        this.content = msg.getContent();
        this.sent_date = new DateTimePack(LocalDateTime.now());
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DateTimePack getSent_date() {
        return sent_date;
    }

    public void setSent_date(DateTimePack sent_date) {
        this.sent_date = sent_date;
    }
}

