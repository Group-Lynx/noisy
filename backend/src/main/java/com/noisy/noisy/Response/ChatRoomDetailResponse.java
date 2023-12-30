package com.noisy.noisy.Response;

import java.util.List;

public class ChatRoomDetailResponse {
    private String name;
    private String owner_name;
    List<UserResponse> members;

    public ChatRoomDetailResponse(String name,String owner_name,List<UserResponse> user){
        this.members=user;
        this.name=name;
        this.owner_name=owner_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public List<UserResponse> getUser() {
        return members;
    }

    public void setUser(List<UserResponse> user) {
        this.members = user;
    }
}
