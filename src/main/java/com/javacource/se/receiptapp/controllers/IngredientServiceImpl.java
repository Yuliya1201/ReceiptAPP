package com.javacource.se.receiptapp.controllers;


import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Service
public class IngredientServiceImpl implements IngredientService {
    private final Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static Integer id = 0;

    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredientMap.containsValue(ingredient)) {
            throw new RuntimeException();
        }
        ingredientMap.put(id++, ingredient);
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(Integer id) {
        if (!ingredientMap.containsKey(id)) {
            throw new RuntimeException("Ингредиент с заданным id Не найден");
        }
        return ingredientMap.get(id);
    }
    @Override
    public Collection<Ingredient> getAll(){
        return ingredientMap.values();
    }
    @Override
    public Ingredient removeIngredient(int id) {
        if (!ingredientMap.containsKey(id)) {
            throw new RuntimeException("Ингредиент с заданным id не найден");
        }
        return ingredientMap.remove(id);
    }
    @Override
    public Ingredient updateIngredient(int id,Ingredient ingredient) {
        if (!ingredientMap.containsKey(id)) {
            throw new RuntimeException("Ингредиент с заданным id не найден");
        }
        ingredientMap.put(id,ingredient);
        return ingredient;
    }
}
