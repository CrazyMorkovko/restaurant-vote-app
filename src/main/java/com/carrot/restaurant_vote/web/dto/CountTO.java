package com.carrot.restaurant_vote.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountTO {
    private final Integer count;
}
