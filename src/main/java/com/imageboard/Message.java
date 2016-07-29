package com.imageboard;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Thread thread;

    private Date date;
    private String content;
    private String image;

    public Message(Thread thread, Date date, String content, String image) {
        this.thread = thread;
        this.date = date;
        this.content = content;
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
    }

    public Thread getThread() {
        return thread;
    }


    public Message(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    protected Message(){}
}
