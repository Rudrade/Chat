package com.example.chat.test.web.controller;

import com.example.chat.dao.ConversationDao;
import com.example.chat.dao.UserDao;
import com.example.chat.model.Role;
import com.example.chat.model.User;
import com.example.chat.web.controller.ConversationController;
import com.example.chat.web.Flash;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import static com.example.chat.web.Flash.Status;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by rui on 24/07/2019
 */

@RunWith(MockitoJUnitRunner.class)
public class ConversationControllerTest {
    private MockMvc mockMvc;
    private User user;

    @InjectMocks
    private ConversationController controller;

    @Spy
    private UserDao userDao;

    @Spy
    private ConversationDao conversationDao;

    @Mock
    private Principal principal;

    @Before
    public void setup() throws Exception {
        controller = new ConversationController();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        controller.setConversationDao(conversationDao);
        controller.setUserDao(userDao);

        user = new User(1L, "utilizador", "password", new Role(1L, "ROLE_USER"), null);

        doReturn(user).when(userDao).getByUsername("utilizador");
        doReturn("user").when(principal).getName();
    }

    @Test
    public void mainPost_SearchSuccess() throws Exception {
        doReturn(false).when(conversationDao).containsUserByUsername("utilizador");

        mockMvc.perform(
                post("/")
                    .param("action", "search")
                    .param("username", "utilizador")
                    .principal(principal))
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attribute("user", user));
    }

    @Test
    public void mainPost_SearchError() throws Exception {
        doReturn(true).when(conversationDao).containsUserByUsername("utilizador");

        MvcResult result = mockMvc.perform(
                post("/")
                        .param("action", "search")
                        .param("username", "utilizador")
                        .principal(principal))
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("flash"))
                .andReturn();

        Flash flash = (Flash) result.getFlashMap().get("flash");

        assertEquals(flash.getStatus(), Status.FAILURE);
    }

    @Test
    public void mainPost_AddSuccess() throws Exception {
        doCallRealMethod().when(conversationDao).addUser(ArgumentMatchers.any(User.class));

        MvcResult result = mockMvc.perform(
                post("/")
                        .param("action", "add")
                        .param("nameUser", "utilizador"))
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("flash"))
                .andExpect(flash().attributeExists("conversationUsers"))
                .andReturn();

        Flash flash = (Flash) result.getFlashMap().get("flash");
        List<User> userList = (List<User>) result.getFlashMap().get("conversationUsers");

        assertEquals(flash.getStatus(), Status.SUCCESS);
        assertThat(userList, hasItem(user));
    }

    @Test
    public void mainPost_RemoveSuccess() throws Exception {
        doCallRealMethod().when(conversationDao).removeByUsername(anyString());

        MvcResult result = mockMvc.perform(
                post("/")
                        .param("action", "remove")
                        .param("nameUser", "utilizador"))
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("conversationUsers"))
                .andExpect(flash().attributeExists("flash"))
                .andReturn();

        Flash flash = (Flash) result.getFlashMap().get("flash");
        List<User> userList = (List<User>) result.getFlashMap().get("conversationUsers");

        assertEquals(flash.getStatus(), Status.SUCCESS);
        assertThat(userList, not(hasItem(user)));
    }

    @Test
    public void mainPost_Cancel() throws Exception {
        doCallRealMethod().when(conversationDao).cancel();

        mockMvc.perform(
                post("/")
                        .param("action", "cancel"));

        assertThat(conversationDao.getUsers(), IsEmptyCollection.empty());
    }

    @Test
    public void main_GetConversations() throws Exception {
        doCallRealMethod().when(userDao).getConversation(ArgumentMatchers.any(User.class));

        when(principal.getName()).thenReturn("utilizador");

        mockMvc.perform(
                get("/")
                    .principal(principal))
                .andExpect(view().name("chat"));

        verify(userDao).getConversation(ArgumentMatchers.any(User.class));
    }
}
