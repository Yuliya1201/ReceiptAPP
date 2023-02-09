package com.javacource.se.receiptapp.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import com.javacource.se.receiptapp.exception.FileProcessingException;
import com.javacource.se.receiptapp.FileService.FilesService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Collection;
import com.javacource.se.receiptapp.exception.IngredientExistsException;


import java.util.HashMap;
import java.util.Map;

@Service
public class IngredientServiceImpl implements IngredientService {
    private final FilesService filesService;

    public IngredientServiceImpl(@Qualifier("ingredientFileService") FilesService filesService) {
        this.filesService = filesService;
    }


    private Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    private static Integer id = 0;


    @Override
    public Ingredient putNewIngredient(Ingredient ingredient) {
        ingredientMap.put(id++, ingredient);
        saveToFileIngredient();
        return ingredient;
    }


    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredientMap.containsValue(ingredient)) {
            throw new IngredientExistsException();
        }
        ingredientMap.put(id++, ingredient);
        saveToFileIngredient();
        return ingredient;
    }
    public void saveToFileIngredient() throws FileProcessingException {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось сохранить");
        }
    }
    @Override
    public void readFromFileIngredient() throws FileProcessingException {
        try {
            String json = filesService.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не читается");
        }
    }
    @Override


        public Ingredient getIngredient(Integer id) {
        if (!ingredientMap.containsKey(id)) {
            throw new NotFoundException("Ингредиент с заданным id Не найден");
        }
        return ingredientMap.get(id);
    }
    @Override

        public Collection<Ingredient> getAll() {
        return ingredientMap.values();
    }
    @Override

        public Ingredient removeIngredient(int id) {
        if (!ingredientMap.containsKey(id)) {
            throw new RuntimeException("Ингредиент с заданным id не найден");
        }
        return ingredientMap.remove(id);
    }
    @Override

        public Ingredient updateIngredient(int id, Ingredient ingredient) {
        if (!ingredientMap.containsKey(id)) {
            throw new NotFoundException("Ингредиент с заданным id не найден");
        }
        ingredientMap.put(id, ingredient);
        saveToFileIngredient();
        return ingredient;
    }
    @Override
    public boolean deleteIngredient(Integer id) {
        if (ingredientMap.containsKey(id)) {
            ingredientMap.remove(id);
            return true;
        }
        return false;
    }
}
