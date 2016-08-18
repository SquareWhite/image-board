package com.squarewhite.imageboard.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.squarewhite.imageboard.configs.CustomDateSerializer;
import com.squarewhite.imageboard.entities.Thread;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.lang.*;
import java.util.Date;

@Relation(value = "thread", collectionRelation = "threads")
public class ThreadResource extends ResourceSupport{

    private final String name;
    private final Date dateUpdated;
    private final boolean closed;

    @JsonCreator
    public ThreadResource(@JsonProperty("name") String name,
                          @JsonProperty("dateUpdated") Date dateUpdated,
                          @JsonProperty("closed") boolean closed) {
        this.name = name;
        this.dateUpdated = dateUpdated;
        this.closed = closed;
    }

    public ThreadResource( Thread thread ){
        this.name = thread.getName();
        this.dateUpdated = thread.getDateUpdated();
        this.closed = thread.isClosed();
    }

    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getDateUpdated() {
        return dateUpdated;
    }

    public String getName() {
        return name;
    }

    public boolean isClosed() {
        return closed;
    }
}
