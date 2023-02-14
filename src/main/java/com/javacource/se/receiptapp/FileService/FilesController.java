package com.javacource.se.receiptapp.FileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
@Tag(name = "Files",description = "CRUD-операции для работы с файлами")

public class FilesController {
    private final FilesService recipeFileService;
    private final FilesService ingredientFileService;

    public FilesController(@Qualifier("ingredientFileService")FilesService ingredientFileService
            ,@Qualifier("recipeFileService")FilesService recipeFileService) {
        this.recipeFileService = recipeFileService;
        this.ingredientFileService = ingredientFileService;
    }
    @GetMapping("/ingredient/export")
    @Operation(description = "Экспорт файла ингредиентов")
    public ResponseEntity<InputStreamResource>downloadIngredientFile() throws IOException {
        InputStreamResource inputStreamResource = ingredientFileService.exportFile();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .contentLength(Files.size(ingredientFileService.getPath())).header(HttpHeaders.CONTENT_DISPOSITION
                        ,"attachment;filename = \"Ingredients.json\"").body(inputStreamResource);
    }
    @PostMapping(value = "/ingredient/import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Импортт файлов ингредиентов")
    public ResponseEntity<Void>uploadDataFileIngredient(@RequestParam MultipartFile file)throws FileNotFoundException {
        ingredientFileService.importFile(file);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/recipe/export")
    @Operation(description = "Экспорт файла рецептов")
    public ResponseEntity<InputStreamResource>downloadRecipeFile()throws IOException {
        InputStreamResource inputStreamResource = recipeFileService.exportFile();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).contentLength(Files.size
                (ingredientFileService.getPath())).header(HttpHeaders.CONTENT_DISPOSITION,"attachment" +
                ";filename = \"Recipes.json\"").body(inputStreamResource);
    }
    @PostMapping(value = "/recipe/import",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(description = "Импорт файла рецептов")
    public ResponseEntity<Void>uploadDataFileRecipe(@RequestParam MultipartFile file) throws FileNotFoundException {
        recipeFileService.importFile(file);
        return ResponseEntity.ok().build();
    }
}
