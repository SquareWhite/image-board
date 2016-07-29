package com.imageboard;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="name")
public class Theme {

    @Id
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "theme", fetch = FetchType.EAGER)
    private Collection<Thread> threads;

    public Theme(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Collection<Thread> getThreads() {
        return threads;
    }

    protected Theme(){}
}
