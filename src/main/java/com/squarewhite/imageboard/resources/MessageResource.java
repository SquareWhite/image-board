package com.squarewhite.imageboard.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.squarewhite.imageboard.configs.CustomDateSerializer;
import com.squarewhite.imageboard.entities.Message;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Date;

@Relation(value = "message", collectionRelation = "messages")
public class MessageResource extends ResourceSupport{

    private final Long messageId;
    private final String content;
    private final String image;
    private final Date date;

    @JsonCreator
    public MessageResource( @JsonProperty("messageId") Long messageId,
                            @JsonProperty("content") String content,
                            @JsonProperty("image") String image,
                            @JsonProperty("date") Date date) {
        this.messageId = messageId;
        this.content = content;
        this.image = image;
        this.date = date;
    }

    public MessageResource(Message message){
        messageId = message.getMessageId();
        content = message.getContent();
        image = message.getImage();
        date = message.getDate();
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getDate() {
        return date;
    }

    public Long getMessageId() {
        return messageId;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }
}
