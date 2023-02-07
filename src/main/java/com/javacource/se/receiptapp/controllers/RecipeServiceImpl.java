package com.javacource.se.receiptapp.controllers;
import com.javacource.se.receiptapp.FileService.FilesService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import com.javacource.se.receiptapp.exception.FileProcessingException;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service("recipeFleService")

    public class RecipeServiceImpl implements FilesService {
    @Value("${path.to.files}")
    private String dataFilePathIngredient;
    @Value("${name.of.recipe.file}")
    private String dataRecipeFileName;


    private final FilesService filesService;
    private final Map<Integer, Recipe> recipeMap = new LinkedHashMap<>();
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
    @Override

    public boolean saveToFileRecipe(String json) {
        try {
            cleanDataFile();
            Files.writeString(Path.of(dataFilePathIngredient,dataRecipeFileName,json));
            return true;
        } catch (IOException e) {
            return false;
        }

    }
    @Override

    public String readFromFile() {
        if (Files.exists(Path.of(dataFilePathIngredient, dataRecipeFileName))) {
            try {
                return Files.readString(Path.of(dataFilePathIngredient, dataRecipeFileName));
            } catch (IOException e) {
                throw new FileProcessingException("не удалось прочитать файл");
            }
        } else {
            return "{}";
        }
    }


    @Override
    public boolean cleanDataFile() {
        try {
            Path path = Path.of(dataFilePathIngredient, dataRecipeFileName);
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
    public Collection<Recipe> getAll(int page, int size) {
        return recipeMap.values();
    }

    @Override
    public LinkedHashMap<Integer, Recipe> removeRecipe(int id) {
        return null;
    }

    @PostConstruct
    private void init() {
        readFromFile();

    }
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
    public Collection<Recipe> getByIngredientId(Integer ingredientId) {
        return null;
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
