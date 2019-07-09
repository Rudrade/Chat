package com.example.chat.web.controller;

import com.example.chat.web.Flash;
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
public class UtilizadorController {
    private static final Logger LOGGER = Logger.getLogger("com.example.chat.web.controller.UtilizadorController");

    @RequestMapping("/")
    public String chat() {
        return "chat";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, HttpServletRequest request) {
        try {
            Object flash = request.getSession().getAttribute("flash");
            LOGGER.info("FLASH IS NULL: " + (flash == null));
            model.addAttribute("flash", flash);
            request.getSession().removeAttribute("flash");
            LOGGER.info("STATUS: " + flash.toString());
        } catch (Exception e) {}
        return "login";
    }
}
