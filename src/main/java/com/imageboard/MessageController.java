package com.imageboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    @Autowired
    MessageRepository messageRepo;

    @RequestMapping(path = "/{messageId}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getMessage(@PathVariable Long messageId){
        Message requestedMessage = messageRepo.findOne(messageId);
        MessageResourceAssembler assembler = new MessageResourceAssembler();
        MessageResource resource = assembler.toResource(requestedMessage);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @RequestMapping(path = "/{messageId}/thread", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getThreadOfMessage(@PathVariable Long messageId) {
        Thread thread = messageRepo.findOne(messageId).getThread();
        ThreadResourceAssembler assembler = new ThreadResourceAssembler();
        ThreadResource resource = assembler.toResource(thread);

        resource.add(linkTo(methodOn(MessageController.class).getThreadOfMessage(messageId)).withSelfRel());
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

}
