package com.example.chat.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rui on 09/07/2019
 */

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @ElementCollection
    @JoinColumn(name = "user_id")
    private List<User> users;

    @NotNull
    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messages;

    public Conversation(Long id, @NotNull List<User> users, @NotNull List<Message> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Conversation() {
        users = new ArrayList<>();
        messages = new ArrayList<>();
    }
}
