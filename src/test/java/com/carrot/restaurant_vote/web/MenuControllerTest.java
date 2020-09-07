package com.carrot.restaurant_vote.web;

import com.carrot.restaurant_vote.JsonUtil;
import com.carrot.restaurant_vote.web.dto.MenuTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuControllerTest extends ControllerTest {
    private final MenuTO MENU = MenuTO.builder().date(LocalDate.now()).build();
    @Test
    void findAllByDate() throws Exception {
        mvc.perform(get("/api/1.0/restaurant/menu/by-date/" + LocalDate.now().minusDays(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(5)));
    }

    @Test
    @WithUserDetails("Regina")
    void findByDate() throws Exception {
        mvc.perform(get("/api/1.0/restaurant/7/menu/by-date/" + LocalDate.now()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(14)));
    }

    @Test
    @WithUserDetails("Regina")
    void create() throws Exception {
        mvc.perform(post("/api/1.0/restaurant/19/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("Regina")
    void createForRestaurantWithMenu() throws Exception {
        mvc.perform(post("/api/1.0/restaurant/7/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("Vadim")
    void createByNotAdmin() throws Exception {
        mvc.perform(post("/api/1.0/restaurant/19/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MENU)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Regina")
    void countVotes() throws Exception {
        mvc.perform(get("/api/1.0/restaurant/menu/14/count-votes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count", is(1)));
    }
}
