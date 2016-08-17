package com.squarewhite.imageboard.resources;

import com.squarewhite.imageboard.controllers.ThreadController;
import com.squarewhite.imageboard.entities.Thread;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
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
        ThreadResource resource = new ThreadResource(thread);

        ControllerLinkBuilder self =
                linkTo( methodOn( ThreadController.class ).getThread( thread.getThreadId() ));

        resource.add(self.withSelfRel());
        resource.add(self.slash("/messages").withRel("messages"));
        resource.add(self.slash("/theme").withRel("theme"));
        return resource;
    }
}
