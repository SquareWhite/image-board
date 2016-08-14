package com.imageboard;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ThreadResourceAssembler extends ResourceAssemblerSupport<Thread, ThreadResource> {

    public ThreadResourceAssembler() {
        super(ThreadController.class, ThreadResource.class);
    }

    @Override
    public ThreadResource toResource(Thread thread) {
        Long id = thread.getThreadId();
        ThreadResource resource = new ThreadResource(thread);

        resource.add(linkTo(methodOn(ThreadController.class).getThread(id)).withSelfRel());
        resource.add(linkTo(methodOn(ThreadController.class).getMessagesOfThread(id)).withRel("messages"));
        resource.add(linkTo(methodOn(ThreadController.class).getThemeOfThread(id)).withRel("theme"));
        return resource;
    }
}
