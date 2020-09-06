package com.carrot.restaurant_vote.web.dto;

import com.carrot.restaurant_vote.models.Menu;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class MenuTO {
    private Integer id;

    @NotNull
    private LocalDate date;
}
