package com.javacource.se.receiptapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacource.se.receiptapp.FileService.FilesService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
@Service
public class IngredientServiceImpl implements IngredientService {
    final private FilesService filesService;
    private static Map<Integer, Ingredient> ingredientMap = new LinkedHashMap<>();
    private static Integer id = 0;

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }
    @PostConstruct
    private void init() {
        readFromFileIngredient();
    }
    @Override
    public boolean cleanDataFileIngredient() {
        try {
            Path path = Path.of(dataFilePath,dataIngredientFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Ingredient putNewIngredient(Ingredient ingredient) {
        ingredientMap.put(id++,ingredient);
        saveToFileIngredient();
        return ingredient;
    }


    @Override
    public Ingredient addIngredient(Ingredient ingredient) {
        if (ingredientMap.containsValue(ingredient)) {
            throw new RuntimeException();
        }
        ingredientMap.put(id++, ingredient);
        return ingredient;
    }

    @Override
    public Ingredient getIngredient(Integer id) {
        if (!ingredientMap.containsKey(id)) {
            throw new RuntimeException("Ингредиент с заданным id Не найден");
        }
        return ingredientMap.get(id);
    }
    @Override
    public Collection<Ingredient> getAll(){
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
    public Ingredient updateIngredient(int id,Ingredient ingredient) {
        if (!ingredientMap.containsKey(id)) {
            throw new RuntimeException("Ингредиент с заданным id не найден");
        }
        ingredientMap.put(id,ingredient);
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
    private void saveToFileIngredient() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesService.saveToFileIngredient(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFileIngredient() {
        String json = filesService.readFromFileIngredient();
        try {
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<Map<Integer, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
