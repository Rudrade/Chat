package com.example.chat.dao;

import com.example.chat.model.Utilizador;
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
public class UtilizadorDao implements UserDetailsService {
    @Autowired
    private SessionFactory sessionFactory;

    public Utilizador getByUsername(String username) {
        Session session = sessionFactory.openSession();
        List<Utilizador> utilizadorList = session.createCriteria(Utilizador.class).list();
        session.close();

        for (Utilizador utilizador : utilizadorList) {
            if (utilizador.getUsername().equals(username)) {
                return utilizador;
            }
        }

        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilizador utilizador = getByUsername(username);
        if (utilizador == null) {
            throw new UsernameNotFoundException("Utilizador não encontrado");
        }
        return utilizador;
    }
}
