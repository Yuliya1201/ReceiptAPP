package com.javacource.se.receiptapp.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import com.javacource.se.receiptapp.FileService.IngredientFileServiceImpl;
import com.javacource.se.receiptapp.exception.FileProcessingException;
import com.javacource.se.receiptapp.exception.IngredientExistsException;
import org.springframework.web.multipart.MultipartFile;

@Service("ingredientFileService")
public class IngredientFileServiceImpl implements FilesService {
        @Value("srs/main/resources")
        private String dataFilePathIngredient;
        @Value("ingredient.json")
        private String dataIngredientFileName;
        private Path path;


        @Override
        public boolean saveToFile(String json) {
            try {
                cleanDataFile();
                Files.writeString(path, json);
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        public String readFromFile() {
            if (Files.exists(path)) {
                try {
                    return Files.readString(path);
                } catch (IOException e) {
                    throw new FileProcessingException("не удалось прочитать файл");
                }
            } else {
                return "{ }";
            }
        }
         @PostConstruct
    private void init() {
        path = Path.of(dataFilePathIngredient,dataIngredientFileName);
    }


     @Override
    public boolean cleanDataFile() {
         try {
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
        return new File(dataFilePathIngredient + "/" + dataIngredientFileName);
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


