package com.squarewhite.imageboard.resources;


import com.squarewhite.imageboard.controllers.ThemeController;
import com.squarewhite.imageboard.entities.Theme;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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
        ThemeResource resource = new ThemeResource(theme);

        ControllerLinkBuilder self =
                linkTo( methodOn( ThemeController.class ).getTheme( theme.getThemeId() ));

        resource.add(self.withSelfRel());
        resource.add(self.slash("/threads").withRel("threads"));
        return resource;
    }
}
