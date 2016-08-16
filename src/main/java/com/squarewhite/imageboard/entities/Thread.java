package com.squarewhite.imageboard.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
public class Thread{

    static final int BUMP_LIMIT = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long threadId;

    @OneToMany(mappedBy = "thread", fetch = FetchType.LAZY)
    private Collection<Message> messages;

    @ManyToOne
    private Theme theme;

    private String name;
    private Date dateUpdated;

    public Thread( Theme theme, String name ) {
        this.theme = theme;
        this.name = name;
    }

    @PrePersist
    void createdAt() {
        this.dateUpdated = new Date();
    }

    Thread () {};
}