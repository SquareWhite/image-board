package com.squarewhite.imageboard.controllers;

import com.squarewhite.imageboard.entities.Theme;
import com.squarewhite.imageboard.entities.Thread;
import com.squarewhite.imageboard.repositories.ThemeRepository;
import com.squarewhite.imageboard.repositories.ThreadRepository;
import com.squarewhite.imageboard.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/themes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThemeController {

    @Autowired
    ThemeRepository themeRepo;
    @Autowired
    ThreadRepository threadRepo;

    @Autowired
    ThemeResourceAssembler themeAssembler;
    @Autowired
    ThreadResourceAssembler threadAssembler;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> listThemes(){
        Iterable<Theme> themes = themeRepo.findAll();
        List<ThemeResource> embeddedResources = themeAssembler.toResources(themes);
        Resources resources = new Resources<>(embeddedResources);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{themeId}")
    public @ResponseBody ResponseEntity<?> getTheme(@PathVariable Long themeId){
        Theme requestedTheme = themeRepo.findOne(themeId);
        ThemeResource resource = themeAssembler.toResource(requestedTheme);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{themeId}/threads")
    public @ResponseBody PagedResources<?> listThreadsOfTheme( @PathVariable Long themeId,
                                                               Pageable pageable,
                                                               PagedResourcesAssembler assembler) {
        Page<Thread> threads = threadRepo.findThreadsByThemeId(themeId, pageable);
        return assembler.toResource(threads, threadAssembler);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{themeId}")
    public @ResponseBody ResponseEntity<?> addThread(@PathVariable Long themeId, @RequestBody Thread thread){
        Theme targetTheme = themeRepo.findOne(themeId);
        thread.setTheme(targetTheme);

        threadRepo.save(thread);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{themeId}")
    public @ResponseBody ResponseEntity<?> deleteTheme(@PathVariable Long themeId){
        themeRepo.delete(themeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
