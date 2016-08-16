package com.squarewhite.imageboard.resources;


import com.squarewhite.imageboard.controllers.ThemeController;
import com.squarewhite.imageboard.entities.Theme;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ThemeResourceAssembler extends ResourceAssemblerSupport<Theme, ThemeResource>{


    public ThemeResourceAssembler() {
        super(ThemeController.class, ThemeResource.class);
    }

    @Override
    public ThemeResource toResource(Theme theme) {
        Long id = theme.getThemeId();
        ThemeResource resource = new ThemeResource(theme);

        resource.add(linkTo(methodOn(ThemeController.class).getTheme(id)).withSelfRel());
        resource.add(linkTo(methodOn(ThemeController.class).listThreadsOfTheme(id)).withRel("threads"));
        return resource;
    }
}
