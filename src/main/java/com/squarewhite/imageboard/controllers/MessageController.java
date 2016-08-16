package com.squarewhite.imageboard.controllers;

import com.squarewhite.imageboard.entities.Message;
import com.squarewhite.imageboard.entities.Thread;
import com.squarewhite.imageboard.repositories.MessageRepository;
import com.squarewhite.imageboard.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    @Autowired
    MessageRepository messageRepo;

    @Autowired
    ThreadResourceAssembler threadAssembler;
    @Autowired
    MessageResourceAssembler messageAssembler;

    @RequestMapping(path = "/{messageId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getMessage(@PathVariable Long messageId){
        Message requestedMessage = messageRepo.findOne(messageId);
        MessageResource resource = messageAssembler.toResource(requestedMessage);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(path = "/{messageId}/thread", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getThreadOfMessage(@PathVariable Long messageId) {
        Thread thread = messageRepo.findOne(messageId).getThread();
        ThreadResource resource = threadAssembler.toResource(thread);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{messageId}")
    public @ResponseBody ResponseEntity<?> deleteMessage(@PathVariable Long messageId){
        messageRepo.delete(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
