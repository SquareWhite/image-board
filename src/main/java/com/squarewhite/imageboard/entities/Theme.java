package com.squarewhite.imageboard.entities;

import lombok.Data;

import javax.persistence.*;
import java.lang.*;
import java.util.Collection;

@Data
@Entity
public class Theme{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long themeId;

    @OneToMany(mappedBy = "theme", fetch = FetchType.LAZY)
    private Collection<Thread> threads;

    private String name;

    public Theme( String name) {
        this.name = name;
    }

    Theme () {};
}
