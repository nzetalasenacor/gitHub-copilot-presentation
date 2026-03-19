package com.senacor.dev.days.ai_assisted_coding_service.recipe.repository;

import com.senacor.dev.days.ai_assisted_coding_service.recipe.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
