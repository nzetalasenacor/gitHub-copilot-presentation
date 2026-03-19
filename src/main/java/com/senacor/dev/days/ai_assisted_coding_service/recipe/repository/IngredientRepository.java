package com.senacor.dev.days.ai_assisted_coding_service.recipe.repository;

import com.senacor.dev.days.ai_assisted_coding_service.recipe.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
