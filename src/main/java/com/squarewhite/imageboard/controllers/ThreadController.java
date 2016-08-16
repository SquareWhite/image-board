package com.squarewhite.imageboard.controllers;

import com.squarewhite.imageboard.entities.Message;
import com.squarewhite.imageboard.entities.Theme;
import com.squarewhite.imageboard.entities.Thread;
import com.squarewhite.imageboard.repositories.MessageRepository;
import com.squarewhite.imageboard.repositories.ThreadRepository;
import com.squarewhite.imageboard.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/threads", produces = MediaType.APPLICATION_JSON_VALUE)
public class ThreadController {

    @Autowired
    ThreadRepository threadRepo;
    @Autowired
    MessageRepository messageRepo;

    @Autowired
    ThemeResourceAssembler themeAssembler;
    @Autowired
    ThreadResourceAssembler threadAssembler;
    @Autowired
    MessageResourceAssembler messageAssembler;

    @RequestMapping(method = RequestMethod.GET, path = "/{threadId}")
    public @ResponseBody ResponseEntity<?> getThread(@PathVariable Long threadId){
        Thread requestedThread = threadRepo.findOne(threadId);
        ThreadResource resource = threadAssembler.toResource(requestedThread);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{threadId}/theme")
    public @ResponseBody ResponseEntity<?> getThemeOfThread(@PathVariable Long threadId){
        Theme requestedTheme = threadRepo.findOne(threadId).getTheme();
        ThemeResource resource = themeAssembler.toResource(requestedTheme);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{threadId}/messages")
    public @ResponseBody ResponseEntity<?> listMessagesOfThread(@PathVariable Long threadId){
        Iterable<Message> messages = threadRepo.findOne(threadId).getMessages();
        List<MessageResource> embeddedResources = messageAssembler.toResources(messages);
        Resources resources = new Resources<>(embeddedResources);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{threadId}")
    public @ResponseBody ResponseEntity<?> addMessage(@PathVariable Long threadId, @RequestBody Message message){
        Thread targetThread = threadRepo.findOne(threadId);
        message.setThread(targetThread);
        messageRepo.save(message);

        if(!message.getContent().equals("sage")) {
            targetThread.setDateUpdated(message.getDate());
            threadRepo.save(targetThread);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{threadId}")
    public @ResponseBody ResponseEntity<?> deleteThread(@PathVariable Long threadId){
        threadRepo.delete(threadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}