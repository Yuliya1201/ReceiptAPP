package com.javacource.se.receiptapp.controllers;

import java.util.Collection;

public interface IngredientService {
    Ingredient putNewIngredient(Ingredients ingredient);

    boolean cleanDataFileIngredient();

    Ingredient putNewIngredient(Ingredient ingredient);

    Ingredient addIngredient(Ingredient ingredient);
    Ingredient getIngredient(Integer id);
    Collection<Ingredient> getAll();
    Ingredient removeIngredient(int id);
    Ingredient updateIngredient(int id,Ingredient ingredient);

    boolean deleteIngredient(Integer id);
}
