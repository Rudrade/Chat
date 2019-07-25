package com.example.chat.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by rui on 09/07/2019
 */

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    public Role() {
    }
}
