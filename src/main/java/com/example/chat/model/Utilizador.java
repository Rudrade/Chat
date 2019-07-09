package com.example.chat.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by rui on 09/07/2019
 */
@Entity
public class Utilizador {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @Size(min = 8)
    private String password;
}
