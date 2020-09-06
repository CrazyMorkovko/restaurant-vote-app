package com.carrot.restaurant_vote.web.dto;

import com.carrot.restaurant_vote.models.User;
import com.carrot.restaurant_vote.validation.UniqueEmail;
import com.carrot.restaurant_vote.validation.UniqueUserName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class CreateUser {
    @NotBlank
    @UniqueUserName
    @Size(min = 1, max = 100)
    private String name;

    @Email
    @NotBlank
    @UniqueEmail
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 8, max = 32)
    private String password;
}
