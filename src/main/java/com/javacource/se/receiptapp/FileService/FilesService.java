package com.javacource.se.receiptapp.FileService;

public interface FilesService {
    boolean saveToFileIngredient(String json);

    boolean saveToFileRecipe(String json);

    String readFromFileIngredient();

    String readFromFileRecipe();
    boolean cleanDataFileIngredient();

    boolean cleanDataFileRecipe();
}
