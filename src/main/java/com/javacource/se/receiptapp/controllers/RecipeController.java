package com.javacource.se.receiptapp.controllers;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.FieldError;
import jdk.jfr.ContentType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
@Tag(name = "Рецепты",description = "CRUD-операции для работы с рецептами")

public class RecipeController {
    private final RecipeService recipeService;
    @Operation(summary = "поиск рецепта по id")
    @ApiResponses(value={
            @ApiResponse(responseCode = "200",description = "Рецепт был найден")})
    @Parameters(value = {@Parameter(name = "id",example =  "1")})
    @GetMapping("/{id")
    ResponseEntity<LinkedHashMap<Integer, Recipe>> getRecipe(@PathVariable Integer id) {

        return  ResponseEntity.ok(recipeService.getRecipe(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех рецептов",description = "Поиск производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode ="200",description = "Рецепты получены")})
    ResponseEntity<Collection<Recipe>> getRecipesByIngredientId() {
        return ResponseEntity.ok(recipeService.getAll());
    }

    @PostMapping
    @Operation(summary = "Добавление рецепта")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Рецепт добавлен")})
    ResponseEntity<Recipe>addRecipe(@Valid @RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.addRecipe(recipe));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение рецепта по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт изменен",
                    content = {
                            @ContentType
                                    (mediaType = "application/json",
                                    schema = @Schema(implementation = Recipe.class)
                            )
                    }
            )
    })

    @Parameters(value = {
            @Parameter(name = "id",
                    example = "1")
    })
    ResponseEntity<Recipe>updateRecipe(@PathVariable Integer id,@Valid @RequestBody Recipe recipe){
        return ResponseEntity.ok(recipeService.updateRecipe(id,recipe));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "удаление рецептов по id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт удален"
            )
    })
    @Parameters(value ={@Parameter(name = "id", example = "1")})
    ResponseEntity<Map<Integer, Recipe>> removeRecipe(@PathVariable Integer id) {
        return ResponseEntity.ok(recipeService.removeRecipe(id));
    }


    @GetMapping
    @Operation(summary = "Получение всех рецептов", description = "Поиск производится без параметров")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Рецепты получены")})
    ResponseEntity<Collection<Recipe>> getRecipeByIngredientId() {
        return ResponseEntity.ok(recipeService.getAll());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
return errors;
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException notFoundException) {
        return notFoundException.getMessage();
    }
}
