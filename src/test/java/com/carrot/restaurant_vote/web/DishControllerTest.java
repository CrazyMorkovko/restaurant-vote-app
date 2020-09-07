package com.carrot.restaurant_vote.web;

import com.carrot.restaurant_vote.JsonUtil;
import com.carrot.restaurant_vote.web.dto.DishTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends ControllerTest {
    private final DishTO DISH = DishTO.builder().name("Noodle").price(10.0).build();

    @Test
    @WithUserDetails("Regina")
    void create() throws Exception {
        mvc.perform(post("/api/1.0/menu/5/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(DISH)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("Vadim")
    void createByNotAdmin() throws Exception {
        mvc.perform(post("/api/1.0/menu/5/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(DISH)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Regina")
    void update() throws Exception {
        var dish = DishTO.builder().name("Noodle").build();
        mvc.perform(put("/api/1.0/menu/14/dish/16")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dish)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("Regina")
    void updateWithInvalidData() throws Exception {
        mvc.perform(put("/api/1.0/menu/14/dish/16")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(DISH)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Vadim")
    void updateByNotAdmin() throws Exception {
        mvc.perform(put("/api/1.0/menu/14/dish/16")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(DISH)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Regina")
    void deleteDish() throws Exception {
        mvc.perform(delete("/api/1.0/menu/5/dish/2"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Vadim")
    void deleteByNotAdmin() throws Exception {
        mvc.perform(delete("/api/1.0/menu/5/dish/2"))
                .andExpect(status().isForbidden());
    }
}
