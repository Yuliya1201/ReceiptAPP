package com.javacource.se.receiptapp.controllers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;


import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service("recipeFleService")

    public class RecipeServiceImpl implements RecipeService {
    @Value("${path.to.files}")
    private String dataFilePathIngredient;
    @Value("${name.of.recipe.file}")
    private String dataRecipeFileName;


    private final RecipeService recipeService;
    private final Map<Integer, Recipe> recipeMap = new LinkedHashMap<>();
    private static Integer id = 0;

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipe.setId(id);
        recipeMap.put(id++, recipe);
        return recipe;
    }

    public RecipeServiceImpl(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Override
    public Recipe getRecipe(Integer id) {
        if (!recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return recipeMap.get(id);
    }

    @Override
    public Collection<Recipe> getAll(int page, int size) {
        return recipeMap.values();
    }

    @Override
    public Recipe removeRecipe(String name) {
        if (recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return recipeMap.remove(id);
    }

    @Override
    public Recipe updateRecipe(Integer id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        recipeMap.put(id, recipe);
        return recipe;
    }

    @Override
    public boolean deleteRecipe(String name) {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            return true;
        }
        return false;
    }
}
