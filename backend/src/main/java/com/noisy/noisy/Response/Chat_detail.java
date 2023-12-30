package com.noisy.noisy.Response;

public class Chat_detail {
    private ChatRoomMemberResponse chat;
    private int unread;
    public Chat_detail(ChatRoomMemberResponse chat,int unread){
        this.chat=chat;
        this.unread=unread;
    }

    public ChatRoomMemberResponse getChat() {
        return chat;
    }

    public void setChat(ChatRoomMemberResponse chat) {
        this.chat = chat;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }
}
