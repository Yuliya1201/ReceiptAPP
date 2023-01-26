package com.javacource.se.receiptapp.controllers;


import org.springframework.stereotype.Service;

import java.io.InvalidClassException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientServiceImpl implements IngredientService {
    private final Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static Integer id = 0;

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        ingredientMap.put(id++, ingredient);
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(Integer id) {
        return ingredientMap.getOrDefault(id, null);

    }
}
