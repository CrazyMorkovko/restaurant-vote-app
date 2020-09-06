package com.carrot.restaurant_vote.web.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class VoteTO {
    private Integer id;

    @NotNull
    private Integer menuId;
}
