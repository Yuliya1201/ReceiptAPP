package com.javacource.se.receiptapp.FileService;
import com.javacource.se.receiptapp.controllers.Recipe;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;

public interface FilesService {
    boolean saveToFile(String json);

    String readFromFile();

    boolean cleanDataFile();

    File getDataFile();
    InputStreamResource exportFile() throws FileNotFoundException;
    void importFile(MultipartFile file) throws FileNotFoundException;
    public Path getPath();
}
