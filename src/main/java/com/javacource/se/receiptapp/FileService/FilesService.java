package com.javacource.se.receiptapp.FileService;
import com.javacource.se.receiptapp.controllers.Recipe;

import java.io.File;
import java.util.Collection;
import java.util.LinkedHashMap;

public interface FilesService {
    boolean saveToFile(String json);

    Recipe addRecipe(Recipe recipe);

    boolean saveToFileRecipe(String json);

    String readFromFile();

    boolean cleanDataFile();

    boolean cleanDataFileIngredient();

    boolean cleanDataFileRecipe();

    File getDataFileTxt();

    Recipe getRecipe(Integer id);

    Collection<Recipe> getAll();

    Collection<Recipe> getAll(int page, int size);

    LinkedHashMap<Integer, Recipe> removeRecipe(int id);

    Recipe updateRecipe(int id, Recipe recipe);

    // @Override
    // public Collection<Recipe>getAll(int page,int size) {
    //return recipeMap.values().stream().skip(long)size * (page - 1)).limit(size).collect(Collectors.toList());
    //}
    Recipe removeRecipe(String name);

    Recipe updateRecipe(Integer id, Recipe recipe);

    Collection<Recipe> getByIngredientId(Integer ingredientId);

    boolean deleteRecipe(String name);
}
