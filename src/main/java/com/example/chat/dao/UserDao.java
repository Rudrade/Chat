package com.example.chat.dao;

import com.example.chat.model.Conversation;
import com.example.chat.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rui on 09/07/2019
 */

@Service
public class UserDao implements UserDetailsService {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Conversation> getConversation(User user) {
        return user.getConversations();
    }

    public User getByUsername(String username) {
        Session session = sessionFactory.openSession();
        List<User> userList = session.createCriteria(User.class).list();
        session.close();

        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilizador não encontrado");
        }
        return user;
    }
}
