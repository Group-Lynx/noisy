package com.noisy.noisy.Response;

import java.util.List;

public class MessageResponseList {
    private List<MessageResponse> messages;

    public MessageResponseList(List<MessageResponse> messages) {
        this.messages = messages;
    }

    public List<MessageResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageResponse> messages) {
        this.messages = messages;
    }
}
