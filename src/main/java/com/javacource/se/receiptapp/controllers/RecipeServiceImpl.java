package com.javacource.se.receiptapp.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacource.se.receiptapp.FileService.FilesService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service

    public class RecipeServiceImpl implements RecipeService {
    private final FilesService filesService;
    private Map<Integer, Recipe> recipeMap = new LinkedHashMap<>();
    private static Integer id = 0;


    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipe.setId(id);
        recipeMap.put(id++, recipe);
        saveToFileRecipe();
        return recipe;
    }

    public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    private void saveToFileRecipe() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesService.saveToFileRecipe(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void readFromFileRecipe() {
        String json = filesService.readFromFileRecipe();
        try {
            return
                    Files.readString(Path.of(dataFilePath, dataRecipeFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean cleanDataFileRecipe() {
        try {
            Path path = Path.of(dataFilePath, dataRecipeFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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

    @PostConstruct
    private void init() {
        readFromFileRecipe();

    }

    // @Override
    // public Collection<Recipe>getAll(int page,int size) {
    //return recipeMap.values().stream().skip(long)size * (page - 1)).limit(size).collect(Collectors.toList());
    //}
    @Override
    public Recipe removeRecipe(String name) {
        if (recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        return recipeMap.remove(id);
    }

    @Override
    public Recipe updateRecipe(Integer id, Recipe recipe) {
        if (recipeMap.containsKey(id)) {
            throw new NotFoundException("рецепт с заданным id не найден");
        }
        recipeMap.put(id, recipe);
        saveToFileRecipe();
        return recipe;
    }

    @Override
    public boolean deleteRecipe(String name) {
        if (recipeMap.containsKey(id)) {
            recipeMap.remove(id);
            return true;
        }
        return false;
    }
}
