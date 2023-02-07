package com.javacource.se.receiptapp.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import com.javacource.se.receiptapp.FileService.IngredientFileServiceImpl;
import com.javacource.se.receiptapp.exception.FileProcessingException;
import com.javacource.se.receiptapp.exception.IngredientExistsException;

    @Service("ingredientFileService")
public class IngredientFileServiceImpl implements FilesService {
        @Value("srs/main/resources")
        private String dataFilePathIngredient;
        @Value("ingredient.json")
        private String dataIngredientFileName;


        @Override
        public boolean saveToFile(String json) {
            try {
                cleanDataFile();
                Files.writeString(Path.of(dataFilePathIngredient, dataIngredientFileName), json);
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public String readFromFile() {
            if (Files.exists(Path.of(dataFilePathIngredient, dataIngredientFileName))) {
                try {
                    return Files.readString(Path.of(dataFilePathIngredient, dataIngredientFileName));
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
    public boolean cleanDataFileIngredient() {
         try {
             Path path = Path.of(dataFilePathIngredient, dataIngredientFileName);
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
        return new File(dataFilePathIngredient + "/" + dataIngredientFileName);
    }
}


