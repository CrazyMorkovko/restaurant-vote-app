package com.carrot.restaurant_vote.web.dto;

import com.carrot.restaurant_vote.security.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleTo {
    @NotNull
    private RoleType role;
}
