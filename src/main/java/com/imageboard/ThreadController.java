package com.imageboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
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

    @RequestMapping(method = RequestMethod.GET, path = "/{threadId}")
    public @ResponseBody ResponseEntity<?> getThread(@PathVariable Long threadId){
        Thread requestedThread = threadRepo.findOne(threadId);
        ThreadResourceAssembler assembler = new ThreadResourceAssembler();
        ThreadResource resource = assembler.toResource(requestedThread);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{threadId}/theme")
    public @ResponseBody ResponseEntity<?> getThemeOfThread(@PathVariable Long threadId){
        Theme requestedTheme = threadRepo.findOne(threadId).getTheme();
        ThemeResourceAssembler assembler = new ThemeResourceAssembler();
        ThemeResource resource = assembler.toResource(requestedTheme);

        resource.add(linkTo(methodOn(ThreadController.class).getThemeOfThread(threadId)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{threadId}/messages")
    public @ResponseBody ResponseEntity<?> getMessagesOfThread(@PathVariable Long threadId){
        Iterable<Message> messages = threadRepo.findOne(threadId).getMessages();
        MessageResourceAssembler assembler = new MessageResourceAssembler();

        List<MessageResource> embeddedResources = assembler.toResources(messages);
        Resources resources = new Resources<>(embeddedResources);

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{threadId}")
    public @ResponseBody ResponseEntity<?> addMessage(@PathVariable Long threadId, @RequestBody Message message){
        Thread targetThread = threadRepo.findOne(threadId);
        message.setThread(targetThread);
        messageRepo.save(message);

        if(message.getContent().contains("sage")) {
            targetThread.setDateUpdated(message.getDate());
            threadRepo.save(targetThread);
        }

        // TODO there's gotta be more than that
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
