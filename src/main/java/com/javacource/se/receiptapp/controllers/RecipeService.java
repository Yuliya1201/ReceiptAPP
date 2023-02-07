package com.javacource.se.receiptapp.controllers;
import java.util.Collection;
import java.util.LinkedHashMap;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);

    boolean cleanDataFileRecipe();

    LinkedHashMap<Integer, Recipe> getRecipe(Integer id);
    Collection<Recipe> getAll();

    Collection<Recipe>getAll(int page, int size);

    LinkedHashMap<Integer, Recipe> removeRecipe(int id);
    Recipe updateRecipe(int id,Recipe recipe);

    // @Override
    // public Collection<Recipe>getAll(int page,int size) {
    //return recipeMap.values().stream().skip(long)size * (page - 1)).limit(size).collect(Collectors.toList());
    //}
    Recipe removeRecipe(String name);

    Recipe updateRecipe(Integer id, Recipe recipe);

    Collection<Recipe>getByIngredientId(Integer ingredientId);

    boolean deleteRecipe(String name);

}
