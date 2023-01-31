package com.javacource.se.receiptapp.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

    public class RecipeServiceImpl implements RecipeService {
    private final IngredientService ingredientService;
    private final Map<Integer,Recipe>recipeMap = new HashMap<>();
    private static Integer id = 0;


    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipe.setId(id);
        recipe.getIngredients().forEach(ingredientService::addIngredient);
        recipeMap.put(id++,recipe);
        return recipe;
    }
    @Override
    public Recipe getRecipe(Integer id) {
        if (!recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return recipeMap.get(id);
    }
    @Override
    public Collection<Recipe>getAll() {
        return recipeMap.values();
    }
    @Override
    public Collection<Recipe>getAll(int page,int size) {
        return recipeMap.values().stream().skip(long)size * (page - 1)).limit(size).collect(Collectors.toList());
    }
    @Override
    public Recipe removeRecipe(int id) {
        if (recipeMap.containsKey(id)) {
            throw new NotFoundException( "рецепт с заданным id не найден");
        }
        return recipeMap.remove(id);
    }
    @Override
    public Recipe updateRecipe(int id,Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return recipeMap.put(id,recipe);
    }
    @Override
    public Collection<Recipe>getByIngredientId(Integer ingredientId) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        return recipeMap.values().stream().filter(recipe -> recipe.getIngredients().contains(ingredient))
                .collect(Collectors.toList());

    }
}
