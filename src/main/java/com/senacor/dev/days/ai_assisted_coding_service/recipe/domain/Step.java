package com.senacor.dev.days.ai_assisted_coding_service.recipe.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "steps", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"recipe_id", "position"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "step_id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(name = "position", nullable = false)
    private Integer position;

    @Column(name = "description", nullable = false)
    private String description;
}
