package com.imageboard;


import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ThemeResourceAssembler extends ResourceAssemblerSupport<Theme, ThemeResource>{


    public ThemeResourceAssembler() {
        super(ThemeController.class, ThemeResource.class);
    }

    @Override
    public ThemeResource toResource(Theme theme) {
        Long id = theme.getThemeId();
        ThemeResource resource = new ThemeResource(theme);

        resource.add(linkTo(methodOn(ThemeController.class).getTheme(id)).withSelfRel());
        resource.add(linkTo(methodOn(ThemeController.class).getThreadsOfTheme(id)).withRel("threads"));
        return resource;
    }
}
