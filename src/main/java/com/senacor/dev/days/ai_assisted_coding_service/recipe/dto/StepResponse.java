package com.senacor.dev.days.ai_assisted_coding_service.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StepResponse {

    @JsonProperty("step_id")
    private Long id;

    private Integer position;

    private String description;
}
