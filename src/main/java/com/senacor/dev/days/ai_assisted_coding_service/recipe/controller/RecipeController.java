package com.senacor.dev.days.ai_assisted_coding_service.recipe.controller;

import com.senacor.dev.days.ai_assisted_coding_service.common.api.ApiResponse;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.dto.RecipeRequest;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.dto.RecipeResponse;
import com.senacor.dev.days.ai_assisted_coding_service.recipe.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<ApiResponse<RecipeResponse>> createRecipe(@Valid @RequestBody RecipeRequest request) {
        log.info("Received request to create recipe with title={}", request.getTitle());
        RecipeResponse response = recipeService.createRecipe(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecipeResponse>> getRecipe(@PathVariable Long id) {
        log.info("Received request to get recipe with id={}", id);
        RecipeResponse response = recipeService.getRecipeById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
