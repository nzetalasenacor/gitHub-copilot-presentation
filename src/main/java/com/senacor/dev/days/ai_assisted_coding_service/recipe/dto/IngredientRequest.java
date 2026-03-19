package com.senacor.dev.days.ai_assisted_coding_service.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
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
public class IngredientRequest {

    @JsonProperty("name")
    @Size(max = 255)
    private String name;

    @JsonProperty("quantity")
    @Size(max = 100)
    private String quantity;

    @JsonProperty("unit")
    @Size(max = 50)
    private String unit;
}
