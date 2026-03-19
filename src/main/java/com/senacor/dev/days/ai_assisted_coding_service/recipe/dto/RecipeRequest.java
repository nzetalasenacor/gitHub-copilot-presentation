package com.senacor.dev.days.ai_assisted_coding_service.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeRequest {

    @NotBlank
    @Size(min = 1, max = 200)
    private String title;

    @Size(max = 1000)
    private String description;

    @JsonProperty("preparation_time_min")
    @Positive
    private Integer preparationTimeMin;

    @JsonProperty("category_id")
    private String categoryId;

    @JsonProperty("image_url")
    @Pattern(regexp = "https?://.+", message = "must be a valid http or https URL")
    private String imageUrl;

    @Valid
    private List<IngredientRequest> ingredients;

    @JsonProperty("steps")
    @NotEmpty
    @Valid
    private List<StepRequest> steps;
}
