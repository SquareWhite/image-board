package com.imageboard;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class MessageResourceAssembler extends ResourceAssemblerSupport<Message, MessageResource>{

    public MessageResourceAssembler() {
        super(MessageRepository.class, MessageResource.class);
    }

    @Override
    public MessageResource toResource(Message message) {
        Long id = message.getMessageId();
        MessageResource resource = new MessageResource(message);

        resource.add(linkTo(methodOn(MessageController.class).getMessage(id)).withSelfRel());
        resource.add(linkTo(methodOn(MessageController.class).getThreadOfMessage(id)).withRel("thread"));
        return resource;
    }
}
