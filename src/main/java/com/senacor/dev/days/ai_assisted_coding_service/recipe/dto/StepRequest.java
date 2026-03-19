package com.senacor.dev.days.ai_assisted_coding_service.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class StepRequest {

    @JsonProperty("position")
    @NotNull
    @Positive
    private Integer position;

    @JsonProperty("description")
    @NotBlank
    private String description;
}
