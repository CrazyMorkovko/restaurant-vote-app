package com.carrot.restaurant_vote.web.dto;

import com.carrot.restaurant_vote.security.RoleType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class RoleTo {
    @NotNull
    private RoleType role;
}
