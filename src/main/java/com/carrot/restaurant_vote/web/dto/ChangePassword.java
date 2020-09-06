package com.carrot.restaurant_vote.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class ChangePassword {
    @NotBlank
    @Size(min = 8, max = 100)
    private String password;
}
