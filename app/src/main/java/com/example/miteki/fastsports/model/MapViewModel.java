package com.example.miteki.fastsports.model;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

public class MapViewModel implements IMessage, MessageContentType {
    private String id;
    private String text;
    private User user;
    private Date createdAt;

    public MapViewModel(String id, User user, String text, Date createdAt) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }


    @Override
    public IUser getUser() {
        return this.user;
    }

    @Override
    public Date getCreatedAt() {
        return this.createdAt;
    }
}
