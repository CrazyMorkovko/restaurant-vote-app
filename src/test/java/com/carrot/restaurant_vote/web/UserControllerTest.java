package com.carrot.restaurant_vote.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @WithAnonymousUser
    void profileWithAnonymous() throws Exception {
        mvc.perform(get("/api/1.0/user/profile"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails("Regina")
    void profile() throws Exception {
        mvc.perform(get("/api/1.0/user/profile"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("bla@bla.com")))
                .andExpect(jsonPath("$.registered", is("2020-09-04T15:19:40")));
    }

    @Test
    void registration() {
    }

    @Test
    void createVote() {
    }

    @Test
    void updateVote() {
    }

    @Test
    void deleteVote() {
    }
}
