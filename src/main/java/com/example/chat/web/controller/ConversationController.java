package com.example.chat.web.controller;

import com.example.chat.dao.ConversationDao;
import com.example.chat.dao.UserDao;
import com.example.chat.model.Conversation;
import com.example.chat.model.User;
import com.example.chat.web.Flash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * Created by rui on 10/07/2019
 */

@Controller
public class ConversationController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ConversationDao conversationDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Model model, Principal principal) {
        List<Conversation> conversationList = userDao.getConversation(userDao.getByUsername(principal.getName()));
        model.addAttribute("conversations", conversationList);
        return "chat";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String mainPost(RedirectAttributes redirectAttributes, HttpServletRequest request, Principal principal, @RequestParam(required = false) String username, @RequestParam String action, @RequestParam(required = false) String nameUser) throws ServletException {
        switch (action) {
            case "search":
                User user = userDao.getByUsername(username);
                if (user != null) {
                    if (!user.getUsername().equals(principal.getName()) && !conversationDao.containsUserByUsername(user.getUsername())) {
                        redirectAttributes.addFlashAttribute("user", user);
                    } else {
                        redirectAttributes.addFlashAttribute("flash", new Flash("Utilizador já está na conversa", Flash.Status.FAILURE));
                    }
                } else {
                    redirectAttributes.addFlashAttribute("flash", new Flash("Utilizador não encontrado", Flash.Status.FAILURE));
                }
                redirectAttributes.addFlashAttribute("conversationUsers", conversationDao.getUsers());
                break;

            case "add":
                conversationDao.addUser(userDao.getByUsername(nameUser));
                redirectAttributes.addFlashAttribute("conversationUsers", conversationDao.getUsers());
                redirectAttributes.addFlashAttribute("flash", new Flash("Utilizador adicionado com sucesso", Flash.Status.SUCCESS));
                break;

            case "remove":
                conversationDao.removeByUsername(nameUser);
                redirectAttributes.addFlashAttribute("conversationUsers", conversationDao.getUsers());
                redirectAttributes.addFlashAttribute("flash", new Flash("Utilizador removido com sucesso", Flash.Status.SUCCESS));
                break;

            case "cancel":
                request.removeAttribute("conversationUsers");
                conversationDao.cancel();
                break;

            case "logout":
                request.logout();
                break;
        }
        return "redirect:/";
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setConversationDao(ConversationDao conversationDao) {
        this.conversationDao = conversationDao;
    }
}
