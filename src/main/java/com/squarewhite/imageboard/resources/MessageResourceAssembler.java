package com.squarewhite.imageboard.resources;

import com.squarewhite.imageboard.controllers.MessageController;
import com.squarewhite.imageboard.entities.Message;
import com.squarewhite.imageboard.repositories.MessageRepository;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class MessageResourceAssembler extends ResourceAssemblerSupport<Message, MessageResource>{

    public MessageResourceAssembler() {
        super(MessageRepository.class, MessageResource.class);
    }

    @Override
    public MessageResource toResource(Message message) {
        MessageResource resource = new MessageResource(message);

        ControllerLinkBuilder self =
                linkTo( methodOn( MessageController.class ).getMessage( message.getMessageId() ));

        resource.add(self.withSelfRel());
        resource.add(self.slash("/thread").withRel("thread"));
        return resource;
    }
}
