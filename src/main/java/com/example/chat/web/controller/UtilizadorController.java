package com.example.chat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rui on 09/07/2019
 */

@Controller
public class UtilizadorController {

    @RequestMapping("/")
    public String chat() {
        return "chat";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
}
