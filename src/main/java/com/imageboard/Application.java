package com.imageboard;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

   @Bean
   CommandLineRunner initDatabase(
           ThemeRepository themeRepository,
           ThreadRepository threadRepository,
           MessageRepository messageRepository) {
       return (args) -> Arrays.asList("theme1", "theme2", "theme3").forEach(
               (themeName) -> {
                   themeRepository.save(new Theme(themeName));
                   Arrays.asList("thread1", "thread2", "thread3").forEach(
                           (threadName) -> {
                               Thread temp = new Thread(
                                       themeRepository.findByName(themeName),
                                       threadName);
                               threadRepository.save(temp);
                               Arrays.asList("Message1", "Message2", "Message3").forEach(
                                       (messageContent) -> messageRepository.save(new Message(
                                               temp,
                                               messageContent,
                                               "Image"
                                       ))
                               );
                           });


               }
       );
   }

}


