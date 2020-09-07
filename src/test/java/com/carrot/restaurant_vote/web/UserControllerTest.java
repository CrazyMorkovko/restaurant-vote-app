package com.carrot.restaurant_vote.web;

import com.carrot.restaurant_vote.JsonUtil;
import com.carrot.restaurant_vote.web.dto.ChangePassword;
import com.carrot.restaurant_vote.web.dto.CreateUser;
import com.carrot.restaurant_vote.web.dto.UpdateEmail;
import com.carrot.restaurant_vote.web.dto.VoteTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalTime;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserControllerTest extends ControllerTest {
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("bla@bla.com")))
                .andExpect(jsonPath("$.registered", is("2020-09-04T15:19:40")));
    }

    @Test
    void registrationWithInvalidData() throws Exception {
        CreateUser user = CreateUser.builder().name("Ivan").email("blu@blu.ru").password("qwer").build();
        mvc.perform(post("/api/1.0/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(user)))
                .andExpect(status().isBadRequest())
                .andExpect(r -> assertTrue(r.getResolvedException() instanceof MethodArgumentNotValidException))
                .andExpect(r -> assertTrue(
                        ((MethodArgumentNotValidException) r.getResolvedException()).hasFieldErrors("password"))
                );
    }

    @Test
    void registration() throws Exception {
        var user = CreateUser.builder().name("Ivan").email("blu@blu.ru").password("qwertyui").build();
        mvc.perform(post("/api/1.0/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(user)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Regina")
    void createVote() throws Exception {
        var vote = VoteTO.builder().menuId(14).build();
        mvc.perform(post("/api/1.0/user/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("Regina")
    void createVoteWithInvalidData() throws Exception {
        var vote = VoteTO.builder().menuId(5).build();
        mvc.perform(post("/api/1.0/user/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Vadim")
    void createVoteSecondTime() throws Exception {
        var vote = VoteTO.builder().menuId(14).build();
        mvc.perform(post("/api/1.0/user/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("Vadim")
    void updateVote() throws Exception {
        var vote = VoteTO.builder().menuId(6).build();
        mvc.perform(put("/api/1.0/user/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(vote)))
                .andExpect(LocalTime.now().isBefore(LocalTime.of(11, 0))
                        ? status().isOk() : status().isForbidden());
    }

    @Test
    @WithUserDetails("Vadim")
    void deleteVote() throws Exception {
        mvc.perform(delete("/api/1.0/user/vote"))
                .andExpect(LocalTime.now().isBefore(LocalTime.of(11, 0))
                        ? status().isNoContent() : status().isForbidden());
    }

    @Test
    @WithUserDetails("Vadim")
    void updateEmail() throws Exception {
        UpdateEmail email = UpdateEmail.builder().email("asd@asd.com").build();
        mvc.perform(put("/api/1.0/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(email)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Vadim")
    void updateEmailWithSameEmail() throws Exception {
        UpdateEmail email = UpdateEmail.builder().email("bla@bla.com").build();
        mvc.perform(put("/api/1.0/user/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(email)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("Vadim")
    void changePassword() throws Exception {
        ChangePassword password = ChangePassword.builder().password("asdqwerty").build();
        mvc.perform(put("/api/1.0/user/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(password)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("Vadim")
    void changePasswordWithIncorrectLength() throws Exception {
        ChangePassword password = ChangePassword.builder().password("asd").build();
        mvc.perform(put("/api/1.0/user/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(password)))
                .andExpect(status().isBadRequest());
    }
}
