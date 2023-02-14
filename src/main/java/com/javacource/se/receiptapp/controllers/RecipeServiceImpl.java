package com.javacource.se.receiptapp.controllers;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import com.javacource.se.receiptapp.exception.FileProcessingException;
import com.javacource.se.receiptapp.FileService.FilesService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@Service
public class RecipeServiceImpl implements RecipeService {

    private final FilesService fileService;

    private Map<Integer, Recipe> recipeMap = new HashMap<>();
    private static Integer id = 0;

    public RecipeServiceImpl(@Qualifier("recipeFileService") FilesService fileService) {
        this.fileService = fileService;
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipeMap.put(id++, recipe);
        saveToFileRecipe();
        return recipe;
    }

    @Override
    public Recipe getRecipe(Integer id) {
        if (!recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return recipeMap.get(id);
    }

    @Override
    public Collection<Recipe> getAll() {
        return recipeMap.values();
    }

    @Override
    public Recipe removeRecipe(int id) {
        if (!recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        Recipe removedRecipe = recipeMap.remove(id);
        saveToFileRecipe();
        return removedRecipe;
    }

    @Override
    public Recipe updateRecipe(int id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        recipeMap.put(id, recipe);
        saveToFileRecipe();
        return recipe;
    }

    @PostConstruct
    private void initRecipe() throws FileProcessingException {
        readFromFileRecipe();
    }

    private void readFromFileRecipe() throws FileProcessingException {
        try {
            String json = fileService.readFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
            });
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось прочитать");
        }
    }

    private void saveToFileRecipe() throws FileProcessingException {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            fileService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Файл не удалось сохранить");
        }
    }
}
