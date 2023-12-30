package com.noisy.noisy.Response;

import java.util.List;

public class ChatRoomResponseList {
    private List<ChatRoomResponse> chatrooms;

    public ChatRoomResponseList(List<ChatRoomResponse> chatrooms) {
        this.chatrooms = chatrooms;
    }

    public List<ChatRoomResponse> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(List<ChatRoomResponse> chatrooms) {
        this.chatrooms = chatrooms;
    }
}
