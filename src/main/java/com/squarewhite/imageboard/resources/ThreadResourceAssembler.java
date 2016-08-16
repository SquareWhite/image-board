package com.squarewhite.imageboard.resources;

import com.squarewhite.imageboard.controllers.ThreadController;
import com.squarewhite.imageboard.entities.Thread;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.lang.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ThreadResourceAssembler extends ResourceAssemblerSupport<Thread, ThreadResource> {

    public ThreadResourceAssembler() {
        super(ThreadController.class, ThreadResource.class);
    }

    @Override
    public ThreadResource toResource(Thread thread) {
        Long id = thread.getThreadId();
        ThreadResource resource = new ThreadResource(thread);

        resource.add(linkTo(methodOn(ThreadController.class).getThread(id)).withSelfRel());
        resource.add(linkTo(methodOn(ThreadController.class).listMessagesOfThread(id)).withRel("messages"));
        resource.add(linkTo(methodOn(ThreadController.class).getThemeOfThread(id)).withRel("theme"));
        return resource;
    }
}
