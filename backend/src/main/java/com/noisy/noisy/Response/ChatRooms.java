package com.noisy.noisy.Response;

import java.util.List;

public class ChatRooms {
    private List<Chat_detail> chatrooms;
    public ChatRooms(List<Chat_detail> chatrooms){
        this.chatrooms=chatrooms;
    }

    public List<Chat_detail> getChatrooms() {
        return chatrooms;
    }

    public void setChatrooms(List<Chat_detail> chatrooms) {
        this.chatrooms = chatrooms;
    }
}
