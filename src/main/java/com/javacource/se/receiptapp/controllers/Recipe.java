package com.javacource.se.receiptapp.controllers;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;
import javax.validation.constraints.Positive;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode

public class Recipe extends LinkedHashMap<Integer, Recipe> {
    @NotBlank
    private String name;
    private Integer id;
    @Positive
    private Integer cookingTime;
    @NotEmpty
    private List<Ingredient> ingredients;
    @NotEmpty
    private List<String> steps;

}
