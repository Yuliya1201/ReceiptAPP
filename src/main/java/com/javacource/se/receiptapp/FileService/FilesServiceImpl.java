package com.javacource.se.receiptapp.FileService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.data.file}")
    private String dataFilePath;
    @Value("${name.of.ingredients.data.file}")
    private String dataIngredientFileName;
    @Value("${name.of.recipe.data.file}")
    private String dataRecipeFileName;

    @Override
    public boolean saveToFileIngredient(String json) {
        try {
             cleanDataFileIngredient();
            Files.writeString(Path.of(dataFilePath,dataIngredientFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    @Override
    public boolean saveToFileRecipe(String json) {
        try {
            cleanDataFileRecipe();
            Files.writeString(Path.of(dataFilePath,dataRecipeFileName),json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public String readFromFileIngredient() {
        try {
           return Files.readString(Path.of(dataFilePath,dataIngredientFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public String readFromFileRecipe() {
        try {
            return Files.readString(Path.of(dataFilePath,dataRecipeFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

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

    public boolean cleanDataFileRecipe() {
        try {
            Path path = Path.of(dataFilePath,dataRecipeFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}


