package com.javacource.se.receiptapp.controllers;
import java.util.Collection;
import java.util.LinkedHashMap;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);
    Recipe getRecipe(Integer id);

    Collection<Recipe>getAll(int page, int size);

    Recipe removeRecipe(String name);

    Recipe updateRecipe(Integer id, Recipe recipe);

    boolean deleteRecipe(String name);

}
