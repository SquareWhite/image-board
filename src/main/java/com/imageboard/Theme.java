package com.imageboard;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "theme", fetch = FetchType.EAGER)
    private Collection<Thread> threads;

    private String name;

    public Theme(String name) {
        this.name = name;
    }

    Theme () {};
}
