package com.javacource.se.receiptapp.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import com.javacource.se.receiptapp.exception.FileProcessingException;
import org.springframework.web.multipart.MultipartFile;


@Service("recipeFileService")

    public class RecipeFileServiceImpl implements FilesService {
    @Value("src/main/resources")
    private String dataFilePathIngredient;
    @Value("recipe.json")
    private String dataRecipeFileName;
     private Path path;


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
        path = Path.of(dataFilePathIngredient,dataRecipeFileName);
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
    public File getDataFile() {
        return new File(dataFilePathIngredient + "/" + dataRecipeFileName);
    }

    @Override
    public InputStreamResource exportFile() throws FileNotFoundException {
            File file = getDataFile();
        return new InputStreamResource(new FileInputStream(file));
    }

    @Override
    public void importFile(MultipartFile file) throws FileNotFoundException {
            cleanDataFile();
        FileOutputStream fos = new FileOutputStream(getDataFile());
        try {
            IOUtils.copy(file.getInputStream(),fos);
        } catch (IOException e) {
            throw new FileProcessingException("Проблема сохранения файла");
        }

    }

    @Override
    public Path getPath() {
        return path;
    }
}



