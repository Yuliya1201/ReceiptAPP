package com.javacource.se.receiptapp.controllers;

import java.util.Collection;

public interface IngredientService {
    Ingredient addIngredient(Ingredient ingredient);
    Ingredient getIngredient(Integer id);
    Collection<Ingredient> getAll();
    Ingredient removeIngredient(int id);
    Ingredient updateIngredient(int id,Ingredient ingredient);
}
