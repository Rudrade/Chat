package com.example.chat.dao;

import com.example.chat.exception.NotEnoughUsers;
import com.example.chat.model.Conversation;
import com.example.chat.model.Message;
import com.example.chat.model.User;
import org.aspectj.weaver.ast.Not;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rui on 10/07/2019
 */

@Service
public class ConversationDao {
    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;

    private Conversation conversation;

    @Transactional
    public Conversation getConversationById(Long id) {
        Session session = sessionFactory.openSession();
        Conversation c = session.get(Conversation.class, id);
        return c;
    }

    @Transactional
    public void build(User user) throws NotEnoughUsers {
        if (conversation.getUsers().size() >= 1) {
            addUser(user);

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(conversation);
            for (User u : conversation.getUsers()) {
                userDao.addConversationToUser(u, conversation);
                session.saveOrUpdate(u);
            }
            session.getTransaction().commit();
            session.close();
            conversation = new Conversation();
        } else {
            throw new NotEnoughUsers();
        }
    }

    @Transactional
    public Conversation insertMessage(Conversation c, User user, String text) {
        c.getMessages().add(new Message(user, text));

        sessionFactory.getCurrentSession().beginTransaction();
        sessionFactory.getCurrentSession().merge(c);
        sessionFactory.getCurrentSession().getTransaction().commit();
        return c;
    }

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
