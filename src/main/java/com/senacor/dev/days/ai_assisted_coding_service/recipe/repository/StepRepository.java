package com.senacor.dev.days.ai_assisted_coding_service.recipe.repository;

import com.senacor.dev.days.ai_assisted_coding_service.recipe.domain.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
}
