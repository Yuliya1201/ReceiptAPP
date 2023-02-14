package com.javacource.se.receiptapp.controllers;
import java.util.Collection;
import java.util.LinkedHashMap;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);
    Recipe getRecipe(Integer id);

    Recipe updateRecipe(int id, Recipe recipe);

    Collection<Recipe> getAll();

    Recipe removeRecipe(int id);

}
