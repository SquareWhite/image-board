package com.squarewhite.imageboard.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class IndexController {
    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }
}
