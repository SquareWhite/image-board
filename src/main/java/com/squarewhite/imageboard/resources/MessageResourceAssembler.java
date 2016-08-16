package com.squarewhite.imageboard.resources;

import com.squarewhite.imageboard.controllers.MessageController;
import com.squarewhite.imageboard.entities.Message;
import com.squarewhite.imageboard.repositories.MessageRepository;
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
        Long id = message.getMessageId();
//        String content = message.getContent();
//        String image = message.getImage();
//        String formattedDate = new DateFormatter().print(message.getDate(), Locale.ENGLISH);

        MessageResource resource = new MessageResource(message);
        resource.add(linkTo(methodOn(MessageController.class).getMessage(id)).withSelfRel());
        resource.add(linkTo(methodOn(MessageController.class).getThreadOfMessage(id)).withRel("thread"));
        return resource;
    }
}
