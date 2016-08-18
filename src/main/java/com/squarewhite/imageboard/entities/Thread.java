package com.squarewhite.imageboard.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
public class Thread{

    public static final int BUMP_LIMIT = 500;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long threadId;

    @OneToMany(mappedBy = "thread", fetch = FetchType.LAZY)
    private Collection<Message> messages;

    @ManyToOne
    private Theme theme;

    private String name;
    private Date dateUpdated;
    private boolean closed;

    public Thread( Theme theme, String name ) {
        this(theme, name, false);
    }

    public Thread( Theme theme, String name, Boolean closed ) {
        this.theme = theme;
        this.name = name;
        this.closed = closed;
    }

    @PrePersist
    void createdAt() {
        this.dateUpdated = new Date();
    }

    Thread () {};
}