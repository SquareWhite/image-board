package com.squarewhite.imageboard.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.squarewhite.imageboard.configs.CustomDateSerializer;
import com.squarewhite.imageboard.entities.Thread;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.lang.*;
import java.util.Date;

@Relation(value = "thread", collectionRelation = "threads")
public class ThreadResource extends ResourceSupport{

    private final String name;

    private final Date dateUpdated;

    @JsonCreator
    public ThreadResource( @JsonProperty("name") String name,
                           @JsonProperty("dateUpdated") Date dateUpdated) {
        this.name = name;
        this.dateUpdated = dateUpdated;
    }

    public ThreadResource( Thread thread ){
        this.name = thread.getName();
        this.dateUpdated = thread.getDateUpdated();
    }

    public String getName() {
        return name;
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getDateUpdated() {
        return dateUpdated;
    }

}
