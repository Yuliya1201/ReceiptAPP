package com.javacource.se.receiptapp.controllers;

import com.javacource.se.receiptapp.exception.FileProcessingException;

import java.io.File;
import java.util.Collection;

public interface IngredientService {

    Ingredient putNewIngredient(Ingredient ingredient);

    Ingredient addIngredient(Ingredient ingredient);

    void readFromFileIngredient() throws FileProcessingException;

    Ingredient getIngredient(Integer id);
    Collection<Ingredient> getAll();
    Ingredient removeIngredient(int id);
    Ingredient updateIngredient(int id,Ingredient ingredient);
     void saveToFileIngredient() throws FileProcessingException;

    boolean deleteIngredient(Integer id);

}
