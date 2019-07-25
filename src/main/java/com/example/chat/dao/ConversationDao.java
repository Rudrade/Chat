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
        conversation.getUsers().add(user);
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
        conversation.getUsers().removeIf(user -> user.getUsername().equals(username));
    }

    public List<User> getUsers() {
        return new ArrayList<>(conversation.getUsers());
    }

    public void cancel() {
        conversation.getUsers().clear();
    }

    public ConversationDao() {
        conversation = new Conversation();
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
