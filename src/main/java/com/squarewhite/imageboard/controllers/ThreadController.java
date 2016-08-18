package com.squarewhite.imageboard.controllers;

import com.squarewhite.imageboard.entities.Message;
import com.squarewhite.imageboard.entities.Theme;
import com.squarewhite.imageboard.entities.Thread;
import com.squarewhite.imageboard.repositories.MessageRepository;
import com.squarewhite.imageboard.repositories.ThreadRepository;
import com.squarewhite.imageboard.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
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
    public @ResponseBody PagedResources<?> listMessagesOfThread(@PathVariable Long threadId,
                                                                @PageableDefault(size = 100) Pageable pageable,
                                                                PagedResourcesAssembler assembler){
        Page<Message> messages = messageRepo.findMessagesByThreadId(threadId, pageable);
        return assembler.toResource(messages, messageAssembler);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{threadId}")
    public @ResponseBody ResponseEntity<?> addMessage(@PathVariable Long threadId, @RequestBody Message message){
        Thread targetThread = threadRepo.findOne(threadId);

        if( targetThread.isClosed() )
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        message.setThread(targetThread);
        messageRepo.save(message);

        updateThreadInfo(targetThread, message);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private void updateThreadInfo(Thread thread, Message message){
        boolean threadNeedsUpdate = false;

        if( !message.getContent().equals("sage") ) {
            thread.setDateUpdated(message.getDate());
            threadNeedsUpdate = true;
        }

        if( thread.getMessages().size() >= Thread.BUMP_LIMIT ) {
            thread.setClosed(true);
            threadNeedsUpdate = true;
        }

        if( threadNeedsUpdate )
            threadRepo.save(thread);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{threadId}")
    public @ResponseBody ResponseEntity<?> deleteThread(@PathVariable Long threadId){
        threadRepo.delete(threadId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
