package com.example.chat.dao;

import com.example.chat.model.Conversation;
import com.example.chat.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by rui on 10/07/2019
 */

@Service
public class ConversationDao {
    private static final Logger LOGGER = Logger.getLogger("com.example.chat.dao.ConversationDao");

    private Conversation conversation;

    public void addUser(User user) {
        LOGGER.info("ADDING USER: " + user.toString());
        conversation.getUsers().add(user);
        LOGGER.info("ADDING USER LIST: " + conversation.getUsers());
    }

    public boolean containsUserByUsername(String username) {
        for (User user : conversation.getUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void removeByUsername(String username) {
        LOGGER.info("REMOVE LIST BEFORE: " + conversation.getUsers());
        conversation.getUsers().removeIf(user -> user.getUsername().equals(username));
        LOGGER.info("REMOVE LIST AFTER: " + conversation.getUsers());
    }

    public List<User> getUsers() {
        return new ArrayList<>(conversation.getUsers());
    }

    public void cancel() {
        LOGGER.info("CLEAR LIST BEFORE: " + conversation.getUsers());
        conversation.getUsers().clear();
        LOGGER.info("CLEAR LIST AFTER: " + conversation.getUsers());
    }

    public ConversationDao() {
        conversation = new Conversation();
    }

}
