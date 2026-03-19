package com.senacor.dev.days.ai_assisted_coding_service.recipe.repository;

import com.senacor.dev.days.ai_assisted_coding_service.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
