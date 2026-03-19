package com.senacor.dev.days.ai_assisted_coding_service.recipe.service;

import com.senacor.dev.days.ai_assisted_coding_service.recipe.domain.Category;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.domain.Ingredient;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.domain.Recipe;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.domain.Step;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.dto.CategoryResponse;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.dto.IngredientRequest;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.dto.IngredientResponse;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.dto.RecipeRequest;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.dto.RecipeResponse;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.dto.StepRequest;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.dto.StepResponse;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.repository.CategoryRepository;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public RecipeResponse createRecipe(RecipeRequest request) {
        validateSteps(request.getSteps());

        Category category = resolveCategory(request.getCategoryId());

        OffsetDateTime now = OffsetDateTime.now();

        Recipe recipe = new Recipe();
        recipe.setCategory(category);
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setPreparationTimeMin(request.getPreparationTimeMin());
        recipe.setImageUrl(request.getImageUrl());
        recipe.setCreatedAt(now);
        recipe.setUpdatedAt(now);

        if (request.getIngredients() != null) {
            for (IngredientRequest ingredientRequest : request.getIngredients()) {
                if (ingredientRequest == null) {
                    continue;
                }
                Ingredient ingredient = new Ingredient();
                ingredient.setRecipe(recipe);
                ingredient.setName(ingredientRequest.getName());
                ingredient.setQuantity(ingredientRequest.getQuantity());
                ingredient.setUnit(ingredientRequest.getUnit());
                recipe.getIngredients().add(ingredient);
            }
        }

        for (StepRequest stepRequest : request.getSteps()) {
            Step step = new Step();
            step.setRecipe(recipe);
            step.setPosition(stepRequest.getPosition());
            step.setDescription(stepRequest.getDescription());
            recipe.getSteps().add(step);
        }

        Recipe saved = recipeRepository.save(recipe);

        return mapToResponse(saved);
    }

    @Transactional
    public RecipeResponse getRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));

        return mapToResponse(recipe);
    }

    private void validateSteps(List<StepRequest> steps) {
        if (steps == null || steps.isEmpty()) {
            throw new RecipeValidationException("steps must contain at least one element");
        }

        List<Integer> positions = steps.stream()
                .map(StepRequest::getPosition)
                .toList();

        Set<Integer> unique = new HashSet<>(positions);
        if (unique.size() != positions.size()) {
            throw new RecipeValidationException("steps.position values must be unique within the recipe");
        }
    }

    private Category resolveCategory(String rawCategoryId) {
        if (rawCategoryId == null || rawCategoryId.isBlank()) {
            return null;
        }
        try {
            long id = Long.parseLong(rawCategoryId);
            return categoryRepository.findById(id).orElse(null);
        } catch (NumberFormatException ex) {
            // Invalid format is treated the same as unknown category: effectively null
            return null;
        }
    }

    private RecipeResponse mapToResponse(Recipe recipe) {
        CategoryResponse categoryResponse = null;
        Category category = recipe.getCategory();
        if (category != null) {
            categoryResponse = CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .build();
        }

        List<IngredientResponse> ingredientResponses = recipe.getIngredients().stream()
                .map(ingredient -> IngredientResponse.builder()
                        .id(ingredient.getId())
                        .name(ingredient.getName())
                        .quantity(ingredient.getQuantity())
                        .unit(ingredient.getUnit())
                        .build())
                .collect(Collectors.toList());

        List<StepResponse> stepResponses = recipe.getSteps().stream()
                .sorted(Comparator.comparing(Step::getPosition))
                .map(step -> StepResponse.builder()
                        .id(step.getId())
                        .position(step.getPosition())
                        .description(step.getDescription())
                        .build())
                .collect(Collectors.toList());

        return RecipeResponse.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .preparationTimeMin(recipe.getPreparationTimeMin())
                .imageUrl(recipe.getImageUrl())
                .category(categoryResponse)
                .ingredients(ingredientResponses)
                .steps(stepResponses)
                .createdAt(recipe.getCreatedAt())
                .updatedAt(recipe.getUpdatedAt())
                .build();
    }
}
