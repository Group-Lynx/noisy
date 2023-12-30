package com.noisy.noisy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ChatRoom {
    @Id
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String owner_name;
    public String getName(){return this.name;}
    public String getOwner_name(){return this.owner_name;}
    public void setName(String name){this.name=name;}
    public void setOwner_name(String owner_name){this.owner_name=owner_name;}

}
