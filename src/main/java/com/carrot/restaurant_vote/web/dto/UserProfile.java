package com.carrot.restaurant_vote.web.dto;

import com.carrot.restaurant_vote.models.Vote;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserProfile {
    private Integer id;
    private String name;
    private String email;
    private LocalDateTime registered;
    private Vote todayVote;
}
