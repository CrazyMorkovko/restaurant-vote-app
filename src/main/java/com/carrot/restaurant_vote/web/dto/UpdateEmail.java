package com.carrot.restaurant_vote.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class UpdateEmail {
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
}
