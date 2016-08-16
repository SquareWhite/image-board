package com.squarewhite.imageboard.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squarewhite.imageboard.entities.Theme;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "theme", collectionRelation = "themes")
public class ThemeResource extends ResourceSupport {

    private String name;

    @JsonCreator
    public ThemeResource( @JsonProperty("name") String name ) {
        this.name = name;
    }

    public ThemeResource(Theme theme){
        name = theme.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
