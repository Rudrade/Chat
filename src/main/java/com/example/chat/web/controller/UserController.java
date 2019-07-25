package com.example.chat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

/**
 * Created by rui on 09/07/2019
 */

@Controller
public class UserController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);
            request.getSession().removeAttribute("flash");
        } catch (Exception e) {}
        return "login";
    }
}
