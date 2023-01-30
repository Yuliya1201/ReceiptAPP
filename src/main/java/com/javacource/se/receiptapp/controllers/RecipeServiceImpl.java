package com.javacource.se.receiptapp.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.NotActiveException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class RecipeServiceImpl implements RecipeService {
    private final IngredientService ingredientService;
    private final Map<Integer,Recipe>recipeMap = new HashMap<>();
    private static Integer id = 0;


    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipeMap.put(id++,recipe);
        return recipe;
    }
    @Override
    public Recipe getRecipe(Integer id) {
        if (!recipeMap.containsKey(id)) {
            throw new Error("рецепт с заданным id не найден");
        }
        return recipeMap.get(id);
    }
    @Override
    public Collection<Recipe>getAll() {
        return recipeMap.values();

    }
    @Override
    public Recipe removeRecipe(int id) {
        if (recipeMap.containsKey(id)) {
            throw new IntegrationProperties.Error( "рецепт с заданным id не найден");
        }
        return recipeMap.remove(id);
    }
    @Override
    public Recipe updateRecipe(int id,Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            throw new Error("рецепт с заданным id не найден");
        }
        return recipeMap.put(id,recipe);
    }
}
