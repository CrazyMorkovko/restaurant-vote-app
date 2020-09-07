package com.carrot.restaurant_vote.web.dto;

import com.carrot.restaurant_vote.validation.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmail {
    @Email
    @NotBlank
    @UniqueEmail
    @Size(max = 100)
    private String email;
}
