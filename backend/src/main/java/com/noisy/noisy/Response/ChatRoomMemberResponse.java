package com.noisy.noisy.Response;

public class ChatRoomMemberResponse {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatRoomMemberResponse(String name) {
        this.name = name;
    }
}
