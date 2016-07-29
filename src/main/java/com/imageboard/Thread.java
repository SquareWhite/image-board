package com.imageboard;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Thread {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Theme theme;

    private String name;
    private Date date;

    @JsonIgnore
    @OneToMany(mappedBy = "thread", fetch = FetchType.EAGER)
    private Collection<Message> messages;

    public Thread(Theme theme, String name, Date date) {
        this.theme = theme;
        this.name = name;
        this.date = date;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public Theme getTheme() {
        return theme;
    }

    public Long getId() {
        return id;
    }

    protected Thread(){}

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }
}