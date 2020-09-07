package com.carrot.restaurant_vote.web;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends ControllerTest {
    @Test
    @WithUserDetails("Regina")
    void findAll() throws Exception {
        mvc.perform(get("/api/1.0/user/10/vote"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(11)))
                .andExpect(jsonPath("$[1].id", is(17)));
    }

    @Test
    @WithUserDetails("Regina")
    void findByDate() throws Exception {
        mvc.perform(get("/api/1.0/user/10/vote/by-date/" + LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(17)));
    }
}
