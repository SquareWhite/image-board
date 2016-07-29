package com.imageboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/")
public class Controller {
    @Autowired
    ThemeRepository themeRepository;
    @Autowired
    ThreadRepository threadRepository;
    @Autowired
    MessageRepository messageRepository;


    @RequestMapping(method = RequestMethod.GET)
    Collection<Theme> getThemes(){
        return themeRepository.findAll();
    }

    @RequestMapping(value = "{themeName}", method = RequestMethod.GET)
    Collection<Thread> getTheme(@PathVariable String themeName) {
        return threadRepository.findByThemeName(themeName);
    }

    @RequestMapping(value = "thread{threadId}", method = RequestMethod.GET)
    Collection<Message> getThread(@PathVariable Long threadId) {
        return messageRepository.findByThreadId(threadId);
    }

    @RequestMapping(value = "thread{threadId}" , method = RequestMethod.POST)
    public @ResponseBody void postMessage(@RequestBody String content,
                                          @RequestBody String image,
                                          @PathVariable Long threadId) {
        Thread messageThread = threadRepository.findOne(threadId);
        messageRepository.save(new Message(messageThread, new Date(), content, image));
    }
}
