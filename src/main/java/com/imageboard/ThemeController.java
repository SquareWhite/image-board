package com.imageboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/themes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThemeController {

    @Autowired
    ThemeRepository themeRepo;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> listThemes(){
        Iterable<Theme> themes = themeRepo.findAll();
        ThemeResourceAssembler assembler = new ThemeResourceAssembler();

        List<ThemeResource> embeddedResources = assembler.toResources(themes);
        Resources resources = new Resources<>(embeddedResources);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{themeId}")
    public @ResponseBody ResponseEntity<?> getTheme(@PathVariable Long themeId){
        Theme requestedTheme = themeRepo.findOne(themeId);
        ThemeResourceAssembler assembler = new ThemeResourceAssembler();
        ThemeResource resource = assembler.toResource(requestedTheme);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{themeId}/threads")
    public @ResponseBody ResponseEntity<?> getThreadsOfTheme(@PathVariable Long themeId){
        Theme theme = themeRepo.findOne(themeId);
        Iterable<Thread> threads = theme.getThreads();
        ThreadResourceAssembler assembler = new ThreadResourceAssembler();

        List<ThreadResource> embeddedResources = assembler.toResources(threads);
        Resources resources = new Resources<>(embeddedResources);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

}
