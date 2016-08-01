package com.imageboard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }
}
