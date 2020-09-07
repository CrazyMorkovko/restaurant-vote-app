package com.carrot.restaurant_vote.web;

import com.carrot.restaurant_vote.JsonUtil;
import com.carrot.restaurant_vote.security.RoleType;
import com.carrot.restaurant_vote.web.dto.RoleTo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class RoleControllerTest extends ControllerTest {
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
        var role = RoleTo.builder().role(RoleType.ADMIN).build();
        mvc.perform(post("/api/1.0/user/18/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(role)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Vadim")
    void addRoleByNotAdmin() throws Exception {
        var role = RoleTo.builder().role(RoleType.ADMIN).build();
        mvc.perform(post("/api/1.0/user/10/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(role)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Regina")
    void deleteRole() throws Exception {
        var role = RoleTo.builder().role(RoleType.ADMIN).build();
        mvc.perform(delete("/api/1.0/user/9/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(role)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Vadim")
    void deleteRoleByNotAdmin() throws Exception {
        var role = RoleTo.builder().role(RoleType.ADMIN).build();
        mvc.perform(delete("/api/1.0/user/9/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(role)))
                .andExpect(status().isForbidden());
    }
}
