package com.senacor.dev.days.ai_assisted_coding_service.recipe.service;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(Long recipeId) {
        super("Recipe with id %d was not found".formatted(recipeId));
    }
}
