package com.carrot.restaurant_vote.web;

import com.carrot.restaurant_vote.JsonUtil;
import com.carrot.restaurant_vote.security.RoleType;
import com.carrot.restaurant_vote.web.dto.RoleTo;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleControllerTest extends ControllerTest {
    private final RoleTo ROLE = RoleTo.builder().role(RoleType.ADMIN).build();

    @Test
    @WithUserDetails("Regina")
    void findAllByUserId() throws Exception {
        mvc.perform(get("/api/1.0/user/10/role"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("USER")));
    }

    @Test
    @WithUserDetails("Vadim")
    void findAllByUserIdByNotAdmin() throws Exception {
        mvc.perform(get("/api/1.0/user/9/role"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Regina")
    void addRole() throws Exception {
        mvc.perform(post("/api/1.0/user/18/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ROLE)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Vadim")
    void addRoleByNotAdmin() throws Exception {
        mvc.perform(post("/api/1.0/user/10/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ROLE)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Regina")
    void deleteRole() throws Exception {
        mvc.perform(delete("/api/1.0/user/9/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ROLE)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Vadim")
    void deleteRoleByNotAdmin() throws Exception {
        mvc.perform(delete("/api/1.0/user/9/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(ROLE)))
                .andExpect(status().isForbidden());
    }
}
