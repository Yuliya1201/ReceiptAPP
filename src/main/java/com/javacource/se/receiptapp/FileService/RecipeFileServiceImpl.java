package com.javacource.se.receiptapp.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.javacource.se.receiptapp.exception.FileProcessingException;




@Service("recipeFileService")

    public class RecipeFileServiceImpl implements FilesService {
    @Value("src/main/resources")
    private String dataFilePathIngredient;
    @Value("recipe.json")
    private String dataRecipeFileName;


        @Override
        public boolean saveToFile(String json) {
            try {
                cleanDataFile();
                Files.writeString(Path.of(dataFilePathIngredient,dataRecipeFileName), json);
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
                return "{ }";
            }
        }
         @PostConstruct
    private void init() {
        readFromFile();
    }


     @Override
    public boolean cleanDataFileRecipe() {
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
    public File getDataFileTxt() {
        return new File(dataFilePathIngredient + "/" + dataRecipeFileName);
    }
}



