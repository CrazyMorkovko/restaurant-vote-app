package com.carrot.restaurant_vote.web;

import com.carrot.restaurant_vote.JsonUtil;
import com.carrot.restaurant_vote.web.dto.RestaurantTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends ControllerTest {
    @Test
    @WithUserDetails("Regina")
    void findById() throws Exception {
        mvc.perform(get("/api/1.0/restaurant/19"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Barbecue")));
    }

    @Test
    @WithUserDetails("Regina")
    void findAll() throws Exception {
        mvc.perform(get("/api/1.0/restaurant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(7)))
                .andExpect(jsonPath("$[1].id", is(8)))
                .andExpect(jsonPath("$[2].id", is(19)));
    }

    @Test
    @WithUserDetails("Regina")
    void create() throws Exception {
        var restaurant = RestaurantTO.builder().name("KFC").build();
        mvc.perform(post("/api/1.0/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("Vadim")
    void createByNotAdmin() throws Exception {
        var restaurant = RestaurantTO.builder().name("KFC").build();
        mvc.perform(post("/api/1.0/restaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Regina")
    void update() throws Exception {
        var restaurant = RestaurantTO.builder().name("Okayama Express").build();
        mvc.perform(put("/api/1.0/restaurant/7")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("Regina")
    void updateWithInvalidData() throws Exception {
        var restaurant = RestaurantTO.builder().name("").build();
        mvc.perform(put("/api/1.0/restaurant/7")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("Vadim")
    void updateByNotAdmin() throws Exception {
        var restaurant = RestaurantTO.builder().name("Okayama Express").build();
        mvc.perform(put("/api/1.0/restaurant/7")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Regina")
    void deleteRestaurant() throws Exception {
        mvc.perform(delete("/api/1.0/restaurant/7"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Vadim")
    void deleteByNotAdmin() throws Exception {
        mvc.perform(delete("/api/1.0/restaurant/7"))
                .andExpect(status().isForbidden());
    }
}
