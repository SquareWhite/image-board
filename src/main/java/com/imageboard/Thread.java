package com.imageboard;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@Data
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "thread", fetch = FetchType.EAGER)
    private Collection<Message> messages;

    @ManyToOne
    private Theme theme;

    private String name;
    private Date date;

    public Thread(Theme theme, String name) {
        this.theme = theme;
        this.name = name;
    }

    @PrePersist
    void createdAt() {
        this.date = new Date();
    }

    Thread () {};
}