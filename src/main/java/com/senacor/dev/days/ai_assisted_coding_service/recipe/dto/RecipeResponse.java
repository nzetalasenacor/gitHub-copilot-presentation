package com.senacor.dev.days.ai_assisted_coding_service.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeResponse {

    @JsonProperty("recipe_id")
    private Long id;

    private String title;

    private String description;

    @JsonProperty("preparation_time_min")
    private Integer preparationTimeMin;

    @JsonProperty("image_url")
    private String imageUrl;

    private CategoryResponse category;

    private List<IngredientResponse> ingredients;

    private List<StepResponse> steps;

    @JsonProperty("created_at")
    private OffsetDateTime createdAt;

    @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}
