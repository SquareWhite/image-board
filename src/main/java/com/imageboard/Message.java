package com.imageboard;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Message{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    @ManyToOne
    private Thread thread;

    private Date date;
    private String content;
    private String image;

    public Message( Thread thread, String content, String image) {
        this.thread = thread;
        this.content = content;
        this.image = image;
    }

    @PrePersist
    void createdAt() {
        this.date = new Date();
    }

    Message () {};
}
